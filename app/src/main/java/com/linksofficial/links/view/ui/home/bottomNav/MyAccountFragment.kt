package com.linksofficial.links.view.ui.home.bottomNav

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentMyAccountBinding
import com.linksofficial.links.viewmodel.MyAccountVM
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyAccountFragment() : Fragment() {

    private lateinit var binding: FragmentMyAccountBinding
    private val myAccountVM: MyAccountVM by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        myAccountVM.readUserDetail().observe(viewLifecycleOwner, {
            myAccountVM.writeUserLD(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = myAccountVM
            lifecycleOwner = this@MyAccountFragment
        }

        binding.ivLogout.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder
            .setTitle("Logout")
            .setMessage("Do you really want to sign out of the application?")
            .setIcon(R.drawable.ic_log_out)
            .setPositiveButton("Yes") { dialogInterface, which ->
                signOut()
            }
            .setNegativeButton("No") { _, _ ->
            }

        val alertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    private fun signOut() {
        Firebase.auth.signOut()
        requireActivity().findNavController(R.id.nav_host_frag)
            .navigate(R.id.action_homeFragment_to_loginFragment)
        Toasty.success(requireActivity(), "Signed out successfully", Toasty.LENGTH_SHORT).show()
    }

}