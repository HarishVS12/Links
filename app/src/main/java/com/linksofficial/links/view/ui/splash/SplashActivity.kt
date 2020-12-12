package com.linksofficial.links.view.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.linksofficial.links.databinding.ActivitySplashBinding
import com.linksofficial.links.view.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

       animateCard()


        lifecycleScope.launch(Dispatchers.Main){
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun animateCard(){

        val cardX = PropertyValuesHolder.ofFloat(View.SCALE_X,2f)
        val cardY = PropertyValuesHolder.ofFloat(View.SCALE_Y,2f)
        val cardAlpha = PropertyValuesHolder.ofFloat(View.ALPHA,1f)

        ObjectAnimator.ofPropertyValuesHolder(binding.cardIcon,cardX,cardY,cardAlpha).apply {
            duration = 1200
            start()
        }.addListener(object:AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                animateText()
            }
        })

    }

    private fun animateText(){
        ObjectAnimator.ofFloat(binding.tvAppName,View.ALPHA,1f).apply {
            duration = 1200
        }.start()
    }
}