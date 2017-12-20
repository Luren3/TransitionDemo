package com.sflin.transitiondemo

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_after_two.*

class ShareElementTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_element_two)
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun init(){
        var url = intent.getIntExtra("url",0)

        Glide.with(this)
                .load(url)
                .apply(RequestOptions().skipMemoryCache(true))
                .into(img)
    }
}
