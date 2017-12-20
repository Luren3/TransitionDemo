package com.sflin.transitiondemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sflin.transitiondemo.R
import com.sflin.transitiondemo.model.Effect


class EffectListAdapter(private val mContext: Context, private val mList: List<Effect>) : RecyclerView.Adapter<EffectListAdapter.ViewHolder>() {

    private lateinit var mOnCallBack: OnCallBack

    interface OnCallBack {

        fun onClick(effect: Effect,view: View)
    }

    fun setOnClickListener(mOnCallBack: OnCallBack) {
        this.mOnCallBack = mOnCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_after_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val effect = mList[position]
        holder.mName.text = effect.name
        Glide.with(mContext)
                .load(effect.uri)
                .apply(RequestOptions().skipMemoryCache(true))
                .into(holder.mImg)
        holder.mImg.setOnClickListener {
            mOnCallBack.onClick(effect,holder.mImg)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mName: TextView = itemView.findViewById(R.id.item_name)
        val mImg: ImageView = itemView.findViewById(R.id.item_img)
    }

}
