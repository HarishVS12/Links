package com.linksofficial.links.view.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentSplashBinding
import com.linksofficial.links.utils.NetworkHelper
import com.linksofficial.links.viewmodel.LinkActivityVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val linkActivityVM: LinkActivityVM by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    private val networkHelper: NetworkHelper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateCard()


        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
            checkInternetConnection()
        }
    }

    private fun checkInternetConnection() {
        if (networkHelper.isNetConnected()) {
            checkMoreThings()
        } else {
            showSnackbar()
        }
    }


    private fun showSnackbar() {
        val mSnackbar = Snackbar.make(
            binding.rootLayout,
            getString(R.string.no_internet_connection),
            Snackbar.LENGTH_INDEFINITE
        )
            .setBackgroundTint(resources.getColor(R.color.primaryColor))
            .setAction(getString(R.string.retry)) { checkInternetConnection() }
            .setActionTextColor(resources.getColor(R.color.white))

        val snackText = (mSnackbar.view).findViewById<TextView>(R.id.snackbar_text)
        snackText.typeface = ResourcesCompat.getFont(requireActivity(), R.font.lato_bold)

        val snackAction = (mSnackbar.view).findViewById<TextView>(R.id.snackbar_action)
        snackAction.typeface = ResourcesCompat.getFont(requireActivity(), R.font.lato_black)
        mSnackbar.show()
    }

    private fun animateCard() {
        val cardX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2f)
        val cardY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2f)
        val cardAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f)
        ObjectAnimator.ofPropertyValuesHolder(binding.cardIcon, cardX, cardY, cardAlpha).apply {
            duration = 1000
            start()
        }.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animateText()
            }
        })
    }

    private fun animateText() {
        ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).apply {
            duration = 1000
        }.start()
    }

    private fun checkMoreThings(){
        val auth = FirebaseAuth.getInstance().currentUser
        if (auth != null) {
            linkActivityVM.readFirstAppOpen().observe(viewLifecycleOwner,{
                if(it) {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }else{
                    findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                }
            })
        }else{
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }

    }

}