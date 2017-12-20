package com.sflin.transitiondemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_before.*

class BeforeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before)
        initClickListener()
    }

    private fun initClickListener(){
        effect_1.setOnClickListener{
            startActivity(Intent(this@BeforeActivity,BeforeTwoActivity::class.java).apply {
                putExtra("type","1")
            })
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        effect_2.setOnClickListener{
            startActivity(Intent(this@BeforeActivity,BeforeTwoActivity::class.java).apply {
                putExtra("type","2")
            })
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }
}
