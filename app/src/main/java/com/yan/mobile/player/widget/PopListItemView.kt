package com.yan.mobile.player.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.yan.mobile.player.R
import com.yan.mobile.player.model.AudioBean
import kotlinx.android.synthetic.main.item_pop.view.*
import org.jetbrains.anko.layoutInflater

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/21 10:34
 *  @description : PopupWindow列表中的item
 */
class PopListItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        context.layoutInflater.inflate(R.layout.item_pop, this)
    }

    fun setData(audioBean: AudioBean) {
        title.text = audioBean.displayName
        artist.text = audioBean.artist
    }

}