package com.sflin.transitiondemo

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sflin.transitiondemo.model.Effect
import kotlinx.android.synthetic.main.activity_after_two.*

class AfterTwoActivity : AppCompatActivity() {

    private lateinit var effect:Effect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_two)
        init()
        initClickListener()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun init(){
        effect = intent.getSerializableExtra("effect") as Effect

        when(effect.name){
            "Explode" ->{
                window.enterTransition = Explode()
                window.exitTransition = Explode()
            }
            "Slide" ->{
                window.enterTransition = Slide()
                window.exitTransition = Slide()
            }
            "Fade" ->{
                window.enterTransition = Fade()
                window.exitTransition = Fade()
            }
        }

        Glide.with(this)
                .load(effect.uri)
                .apply(RequestOptions().skipMemoryCache(true))
                .into(img)
    }

    private fun initClickListener(){

    }

    override fun onBackPressed() {
        when(effect.name){
            "makeCustom" ->{
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//                ActivityCompat.finishAfterTransition(this@AfterTwoActivity)
            }
            else -> {
                super.onBackPressed()
                ActivityCompat.finishAfterTransition(this)
            }
        }
    }
}
