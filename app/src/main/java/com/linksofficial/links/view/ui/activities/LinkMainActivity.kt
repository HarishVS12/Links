package com.linksofficial.links.view.ui.activities

import android.app.KeyguardManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
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
        setWindow()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        linkActivityVM.getUserDetails(Firebase.auth.currentUser?.uid)

        linkActivityVM.writeLinkCopied(true)
    }


    private fun setWindow(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1){
            setTurnScreenOn(true)
            setShowWhenLocked(true)
            val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this,null)
        }else{
            window.addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }
    }

    override fun onResume() {
        super.onResume()

    }

}