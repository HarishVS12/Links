package com.linksofficial.links.view.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.linksofficial.links.R
import com.linksofficial.links.databinding.ActivityMainBinding
import com.linksofficial.links.databinding.ContainerOnboardingBinding
import com.linksofficial.links.databinding.FragmentOnboardingBinding


class LinkMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

    }

}