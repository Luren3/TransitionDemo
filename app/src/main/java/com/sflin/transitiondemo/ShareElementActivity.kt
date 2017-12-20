package com.sflin.transitiondemo

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.sflin.transitiondemo.adapter.ShareElementListAdapter
import kotlinx.android.synthetic.main.activity_share_element.*

class ShareElementActivity : AppCompatActivity() {

    private lateinit var mListData:ArrayList<Int>

    private lateinit var mAdapter: ShareElementListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_element)
        init()
        initClickListener()
    }

    private fun init(){
        initListData()
        mAdapter = ShareElementListAdapter(this@ShareElementActivity,mListData)

        mAdapter.setOnClickListener(object : ShareElementListAdapter.OnCallBack{
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onClick(view: View, url:Int) {
                startActivity(Intent(this@ShareElementActivity, ShareElementTwoActivity::class.java).apply {

                    putExtra("url",url)

                }, ActivityOptions.makeSceneTransitionAnimation(this@ShareElementActivity,view,"shareImg").toBundle())
            }
        })

        list.layoutManager = GridLayoutManager(this,2)

        list.adapter = mAdapter
    }

    private fun initListData(){
        mListData = ArrayList()
        mListData.add(R.mipmap.img1)
        mListData.add(R.mipmap.img2)
        mListData.add(R.mipmap.img3)
        mListData.add(R.mipmap.img4)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initClickListener(){

        img5.setOnClickListener{
            var one = android.support.v4.util.Pair<View, String>(img5, "shareImg5")

            var two = android.support.v4.util.Pair<View, String>(img6, "shareImg6")

            var pairs = arrayOf(one,two)

            val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
            startActivity(Intent(this@ShareElementActivity, ShareElementThreeActivity::class.java),
                    transitionActivityOptions.toBundle())
        }
        img6.setOnClickListener{
            var one = android.support.v4.util.Pair<View, String>(img5, "shareImg5")

            var two = android.support.v4.util.Pair<View, String>(img6, "shareImg6")

            var pairs = arrayOf(one,two)

            val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
            startActivity(Intent(this@ShareElementActivity, ShareElementThreeActivity::class.java),
                    transitionActivityOptions.toBundle())
        }
    }

}
