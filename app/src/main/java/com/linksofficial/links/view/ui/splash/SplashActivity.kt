package com.linksofficial.links.view.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.linksofficial.links.R
import com.linksofficial.links.databinding.ActivitySplashBinding
import com.linksofficial.links.utils.NetworkHelper
import com.linksofficial.links.view.ui.main.LinkMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val networkHelper: NetworkHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateCard()


        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
            checkInternetConnection()
        }
    }

    private fun checkInternetConnection() {
        if (networkHelper.isNetConnected()) {
            startActivity(Intent(this@SplashActivity, LinkMainActivity::class.java))
            finish()
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
        snackText.typeface = ResourcesCompat.getFont(this, R.font.lato_bold)

        val snackAction = (mSnackbar.view).findViewById<TextView>(R.id.snackbar_action)
        snackAction.typeface = ResourcesCompat.getFont(this, R.font.lato_black)
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
}