package com.yan.mobile.player.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.itheima.player.model.bean.YueDanBean
import com.squareup.picasso.Picasso
import com.yan.mobile.player.R
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_yuedan.view.*
import org.jetbrains.anko.layoutInflater

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:13
 *  @description ：悦单界面的Item
 */
class YueDanItemView: RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        context.layoutInflater.inflate(R.layout.item_yuedan, this)
    }

    fun setData(data: YueDanBean.PlayListsBean) {
        //歌单名称
        title.text = data.title
        //创建者
        author_name.text = data.creator?.nickName
        //歌曲个数
        count.text = data.videoCount.toString()
        //背景图
        Picasso.with(context).load(data.playListBigPic).into(bg)
        //头像
        Picasso.with(context).load(data.creator?.largeAvatar).transform(CropCircleTransformation()).into(author_image)
    }
}