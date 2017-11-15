package com.yan.mobile.player.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.itheima.player.model.bean.VideosBean
import com.squareup.picasso.Picasso
import com.yan.mobile.player.R
import kotlinx.android.synthetic.main.item_mv.view.*
import org.jetbrains.anko.layoutInflater

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 17:55
 *  @description : MV界面单个条目
 */
class MvItemView: RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        context.layoutInflater.inflate(R.layout.item_mv, this)
    }

    fun setData(itemBean: VideosBean) {
        //歌手名称
        artist.text = itemBean.artistName
        //歌曲名称
        title.text = itemBean.title
        //背景图
        Picasso.with(context).load(itemBean.playListPic).into(bg)
    }
}