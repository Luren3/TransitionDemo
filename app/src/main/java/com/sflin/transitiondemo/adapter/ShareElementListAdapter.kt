package com.sflin.transitiondemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sflin.transitiondemo.R


class ShareElementListAdapter(private val mContext: Context, private val mList: List<Int>) : RecyclerView.Adapter<ShareElementListAdapter.ViewHolder>() {

    private lateinit var mOnCallBack: OnCallBack

    interface OnCallBack {

        fun onClick(view: View,url:Int)
    }

    fun setOnClickListener(mOnCallBack: OnCallBack) {
        this.mOnCallBack = mOnCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_share_element_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = mList[position]
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions().skipMemoryCache(true))
                .into(holder.mImg)
        holder.mImg.setOnClickListener {
            mOnCallBack.onClick(holder.mImg,url)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mImg: ImageView = itemView.findViewById(R.id.item_img)
    }

}
