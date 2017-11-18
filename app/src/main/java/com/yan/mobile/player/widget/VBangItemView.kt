package com.yan.mobile.player.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.yan.mobile.player.R
import com.yan.mobile.player.model.AudioBean
import kotlinx.android.synthetic.main.item_vbang.view.*
import org.jetbrains.anko.layoutInflater

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/16 11:39
 *  @description : v榜界面条目view
 */
class VBangItemView: RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        context.layoutInflater.inflate(R.layout.item_vbang, this)
    }

    fun setData(itemBean: AudioBean) {
        music_name.text = itemBean.displayName
        artist.text = itemBean.artist
        size.text = Formatter.formatFileSize(context, itemBean.size)
    }
}