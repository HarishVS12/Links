package com.linksofficial.links.view.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.ContainerFeedBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.view.adapter.FeedContainerAdapter
import com.linksofficial.links.viewmodel.FeedVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedObjectFragment() : Fragment() {

    private lateinit var binding: ContainerFeedBinding
    private val feedVM: FeedVM by viewModel()
    private lateinit var adapter: FeedContainerAdapter

    private var isScrolling: Boolean = true
    private var isLastItemReached: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContainerFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = FeedContainerAdapter(feedVM)
        binding.recyclerView.adapter = adapter

        var linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = linearLayoutManager

        var tag = Tags()
        var tagNames: String? = ""

        arguments?.takeIf { it.containsKey(ConstantsHelper.FEED_VP_ARG) }.apply {
            this?.getInt(ConstantsHelper.FEED_VP_ARG)?.let {
                var tagName = ConstantsHelper.getTagList()[it]
                tagName.tagName?.let { it1 ->
                    updateUI(it1)
                }
                tagNames = tagName?.tagName
            }

        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            tag.tagName?.let { it -> updateUI(it) }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        feedVM.postList.observe(viewLifecycleOwner, {
            binding.cardProgress.visibility = View.GONE
            adapter.submitList(it)
        })

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisItem = linearLayoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount

                if (isScrolling && (firstVisItem + visibleItemCount == totalItemCount) && !isLastItemReached) {
                    isScrolling = false
                    loadMoreItems(tagNames)
                }
            }
        })

    }

    private fun loadMoreItems(tagName: String?) {
        val db = Firebase.firestore
        db.collection(ConstantsHelper.POST)
            .whereEqualTo("_public", true)
            .whereEqualTo("tag", tagName)
            .orderBy("created_at", Query.Direction.DESCENDING)
            .startAfter(feedVM.lastDocSnap!!)
            .limit(4)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty && feedVM.lastDocSnap != it.result.documents.get(
                        it.result.size() - 1
                    )
                ) {
                    feedVM.lastDocSnap = it.result.documents.get(it.result.size() - 1)
                    val posts = mutableListOf<Post>()
                    for (document in it.result) {
                        posts.add(document.toObject(Post::class.java))
                    }
                    feedVM.postList.value?.addAll(posts)
                    feedVM.postList.notifyObserver()
                    if (it.result.size() < 1) {
                        isLastItemReached = true
                        return@addOnCompleteListener
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }


    fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(item)
        this.value = oldValue
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun updateUI(tagText: String) {
        Timber.d("Posts:(TEXT) = $tagText")
        feedVM.getPostFromRemote(tagText)
    }

}