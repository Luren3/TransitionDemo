package com.sflin.transitiondemo

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

class BeforeTwoActivity : AppCompatActivity() {

    private lateinit var type:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before_two)
        init()
    }

    private fun init(){
        type = intent.getStringExtra("type")
        type = "1"
    }

    override fun onBackPressed() {
        when(type){
            "1" ->{
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                ActivityCompat.finishAfterTransition(this@BeforeTwoActivity)
            }
            "2" ->{
                finish()
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
        }
    }
}
