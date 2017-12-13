package com.yan.mobile.player.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import com.yan.mobile.player.R
import com.yan.mobile.player.model.HomeItemBean
import kotlinx.android.synthetic.main.item_home.view.*
import org.jetbrains.anko.layoutInflater

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 17:36
 *  @description ：首页RecyclerView的Item
 */
class HomeItemView: RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        context.layoutInflater.inflate(R.layout.item_home, this)
    }

    fun setData(homeItemBean: HomeItemBean) {
        title.text = homeItemBean.title
        desc.text = homeItemBean.description

        val posterPic = getRealUrl(homeItemBean.posterPic)
        Picasso.with(context).load(posterPic).into(bg)
    }

    private fun getRealUrl(posterPic: String?): String {
        return if (posterPic == null) ""
            else if ("http" in posterPic) posterPic
            else "http:" + posterPic
    }

}