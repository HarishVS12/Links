package com.linksofficial.links.view.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.User
import com.linksofficial.links.databinding.FragmentLoginBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.viewmodel.LinkActivityVM
import com.linksofficial.links.viewmodel.LoginVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var auth: FirebaseAuth

    private val loginVM: LoginVM by viewModel()
    private val linkActivityVM: LinkActivityVM by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        signInRequest()

        binding.btnSignIn.setOnClickListener {
            signInIntent()
            binding.cardProgress.visibility = View.VISIBLE
        }

    }

    private fun signInRequest() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    private fun signInIntent() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, ConstantsHelper.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == ConstantsHelper.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Timber.e("${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    binding.cardProgress.visibility = View.GONE
                    observeFirstAppOpenRead()
                }
            }
            .addOnFailureListener {
                Timber.e("${it.message}")
            }
    }

    private fun createUser(uniqueId: String?, user: FirebaseUser?): User =
        User(
            uniqueId,
            user?.displayName,
            user?.email,
            user?.photoUrl.toString(),
            Timestamp(Date())
        )


    private fun observeFirstAppOpenRead() {
        val currentUser = auth.currentUser
        val uniqueId = currentUser?.uid
        linkActivityVM.readFirstAppOpen().observe(viewLifecycleOwner, {
            if (it == false) {
                loginVM.writeUserLogin(createUser(uniqueId, currentUser), uniqueId)
                findNavController().navigate(R.id.action_loginFragment_to_onboardingFragment)
            }else{
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })
    }

}