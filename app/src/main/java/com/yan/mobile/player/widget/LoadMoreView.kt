package com.yan.mobile.player.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.yan.mobile.player.R
import org.jetbrains.anko.layoutInflater

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 21:13
 *  @description ：加载更多布局
 */
class LoadMoreView: RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        context.layoutInflater.inflate(R.layout.item_load_more, this)
    }
}