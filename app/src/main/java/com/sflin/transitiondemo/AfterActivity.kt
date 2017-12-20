package com.sflin.transitiondemo

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sflin.transitiondemo.adapter.EffectListAdapter
import com.sflin.transitiondemo.model.Effect
import kotlinx.android.synthetic.main.activity_after.*
import java.io.Serializable


class AfterActivity : AppCompatActivity() {

    private lateinit var mListData:ArrayList<Effect>

    private lateinit var mAdapter:EffectListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after)
        init()
    }

    private fun init(){
        initListData()
        mAdapter = EffectListAdapter(this@AfterActivity,mListData)

        mAdapter.setOnClickListener(object :EffectListAdapter.OnCallBack{
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onClick(effect: Effect,view: View) {

                when(effect.name){
                    "makeCustom" ->{
                        ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java).apply {

                            putExtra("effect",effect as Serializable)

                        },ActivityOptionsCompat.makeCustomAnimation(this@AfterActivity,
                                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
                    }
                    "makeScaleUp" ->{
                        val options = ActivityOptionsCompat.makeScaleUpAnimation(view,view.width/2,
                                view.height/2,0, 0)

                        ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java).apply {

                            putExtra("effect",effect as Serializable)

                        },options.toBundle())
                    }
                    "makeThumbnailScaleUp" ->{
                        var bitmap = BitmapFactory.decodeResource(resources,effect.uri)
                        val options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, bitmap,
                                view.width/2, view.height/2)

                        ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java).apply {

                            putExtra("effect",effect as Serializable)

                        },options.toBundle())
                    }
                    "makeClipReveal" ->{
                        val options = ActivityOptionsCompat.makeClipRevealAnimation(view,view.width/2,
                                view.height/2,0, 0)

                        ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java).apply {

                            putExtra("effect",effect as Serializable)

                        },options.toBundle())
                    }
                    else ->{
                        startActivity(Intent(this@AfterActivity, AfterTwoActivity::class.java).apply {

                            putExtra("effect",effect as Serializable)

                        }, ActivityOptionsCompat.makeSceneTransitionAnimation(this@AfterActivity).toBundle())
                    }
                }
            }
        })

        list.layoutManager = LinearLayoutManager(this)

        list.adapter = mAdapter
    }

    private fun initListData(){
        mListData = ArrayList()
        mListData.add(getEffect("Explode",R.mipmap.img1))
        mListData.add(getEffect("Slide",R.mipmap.img2))
        mListData.add(getEffect("Fade",R.mipmap.img3))
        mListData.add(getEffect("makeCustom",R.mipmap.img4))
        mListData.add(getEffect("makeScaleUp",R.mipmap.img5))
        mListData.add(getEffect("makeThumbnailScaleUp",R.mipmap.img6))
        mListData.add(getEffect("makeClipReveal",R.mipmap.img1))
    }

    private fun getEffect(name:String,url:Int): Effect {

        return Effect(name,url)
    }
}
