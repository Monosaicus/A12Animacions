package com.aleix.a12animacions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import com.aleix.a12animacions.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Inicia l'animació després de 1 segon
        binding.imgViewBall.postDelayed({
            startAnimation()
        }, 1000)

        binding.imgViewBall.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            overridePendingTransition(R.anim.hold, R.anim.fade_in);
        }
    }

    // Inicia l'animació de la pilota, fa que vagi de cantonda
    // en cantonada de la pantalla fins a posicionar-se al centre
    private fun startAnimation() {
        val topRightCorner = binding.splashScreenLayout.right - binding.imgViewBall.width
        val bottomRightCorner = binding.splashScreenLayout.bottom - binding.imgViewBall.height
        val bottomLeftCorner = binding.splashScreenLayout.left
        val topLeftCorner = binding.splashScreenLayout.top
        val midX = binding.splashScreenLayout.right / 2 - binding.imgViewBall.width / 2
        val midOffsetY = (binding.splashScreenLayout.bottom / 2) * 0.5

        // Definim les animacions
        val anim1 =
            ObjectAnimator.ofFloat(binding.imgViewBall, "translationX", topRightCorner.toFloat())
        val anim2 =
            ObjectAnimator.ofFloat(binding.imgViewBall, "translationY", bottomRightCorner.toFloat())
        val anim3 =
            ObjectAnimator.ofFloat(binding.imgViewBall, "translationX", bottomLeftCorner.toFloat())
        val anim4 =
            ObjectAnimator.ofFloat(binding.imgViewBall, "translationY", topLeftCorner.toFloat())

        // Definim la durada de les animacions
        anim1.duration = 1000
        anim2.duration = 1000
        anim3.duration = 1000
        anim4.duration = 1000

        // Executem el primer set d'animacions
        val animatorSet1 = AnimatorSet()
        animatorSet1.playSequentially(anim1, anim2, anim3, anim4)
        animatorSet1.start()

        // Executem la segona animació quan acaba el primer set
        animatorSet1.doOnEnd {
            val anim5 = binding.imgViewBall.animate() as ViewPropertyAnimator
            anim5.translationX(midX.toFloat())
                .translationY(midOffsetY.toFloat())
                .scaleX(2f)
                .scaleY(2f).duration = 1000
        }

    }
}