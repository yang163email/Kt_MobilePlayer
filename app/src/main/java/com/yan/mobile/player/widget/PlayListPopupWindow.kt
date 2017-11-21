package com.yan.mobile.player.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.yan.mobile.player.R
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.windowManager

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/18 17:11
 *  @description : 播放列表的 PopupWindow
 */
class PlayListPopupWindow(context: Context,
                          adapter: BaseAdapter,
                          listener: AdapterView.OnItemClickListener,
                          val window: Window): PopupWindow() {
    //记录初始PopupWindow的透明度
    var alpha = 0f
    init {
        alpha = window.attributes.alpha
        val view = context.layoutInflater.inflate(R.layout.pop_play_list, null)
        //获取listview
        val listView = view.find<ListView>(R.id.list_view)
        //适配
        listView.adapter = adapter
        //设置点击监听
        listView.onItemClickListener = listener
        contentView = view
        //设置宽高
        width = ViewGroup.LayoutParams.MATCH_PARENT
        //先获取屏幕的高度
        val wm = context.windowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        //设置成屏幕高度的3/5
        height = (point.y * 3)/ 5
        //获取焦点
        isFocusable = true
        //外部可点击
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable())
        //设置动画效果
        animationStyle = R.style.pop
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        val attributes = window.attributes
        attributes.alpha = 0.4f
        window.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        val attributes = window.attributes
        attributes.alpha = alpha
        window.attributes = attributes
    }
}