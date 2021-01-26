package com.linksofficial.links.view.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.databinding.ActivityMainBinding
import com.linksofficial.links.viewmodel.LinkActivityVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class LinkMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val linkActivityVM: LinkActivityVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        linkActivityVM.getUserDetails(Firebase.auth.currentUser?.uid)

    }

}