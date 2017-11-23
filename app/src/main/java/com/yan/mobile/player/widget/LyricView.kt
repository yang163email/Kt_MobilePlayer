package com.yan.mobile.player.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.yan.mobile.player.R
import com.yan.mobile.player.model.LyricBean
import com.yan.mobile.player.utils.LyricLoader
import com.yan.mobile.player.utils.LyricUtil
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.doAsync

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/21 16:43
 *  @description : 歌词控件
 */
class LyricView: View {
    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val list by lazy { ArrayList<LyricBean>() }

    private var centerLine = 0
    var lineHeight = 0

    var duration = 0
    var progress = 0

    //控件的宽高
    private var viewW = 0
    private var viewH = 0

    var bigSize = 0f
    var smallSize = 0f
    var white = 0
    var green = 0

    /** 是否通过进度更新 */
    var updateByPro = true

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        bigSize = resources.getDimension(R.dimen.bigSize)
        smallSize = resources.getDimension(R.dimen.smallSize)
        white = resources.getColor(R.color.white)
        green = resources.getColor(R.color.green)
        lineHeight = resources.getDimensionPixelOffset(R.dimen.lineHeight)

        //设置画笔居中，x方向
        paint.textAlign = Paint.Align.CENTER

        //循环添加歌词bean
//        for (i in 0 until 30) {
//            list.add(LyricBean(2000* i, "正在播放第$i 行歌词"))
//        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (list.isEmpty()) {
            //数据还没有
            drawSingleText(canvas)
        } else {
            drawMultiText(canvas)
        }
    }

    var offsetY = 0f

    /**
     * 绘制多行文本
     */
    private fun drawMultiText(canvas: Canvas?) {
        if (updateByPro) {
            //通过进度计算得到偏移Y值
            //行可用时间
            var lineTime = 0
            if (centerLine == list.size - 1) {
                //如果是最后一行居中
                lineTime = duration - list[centerLine].startTime
            } else {
                //其他行
                val centerS = list[centerLine].startTime
                val nextS = list[centerLine + 1].startTime
                lineTime = nextS - centerS
            }
            //计算偏移时间
            val offsetTime = progress - list[centerLine].startTime
            //偏移百分比
            val offsetPercent = offsetTime / (lineTime).toFloat()
            //偏移y值
            offsetY = offsetPercent * lineHeight
        }

        val text = list[centerLine].content
        val bounds = Rect()
        //获取绘制的文字信息
        paint.getTextBounds(text, 0, text.length, bounds)
        val centerY = viewH / 2 + bounds.height() / 2 -offsetY
        list.forEachWithIndex { i, lyricBean ->
            //边界判断
            if (y < 0) return
            if (y > viewH + lineHeight) return@forEachWithIndex
            //设置画笔属性
            if (i == centerLine) {
                paint.color = green
                paint.textSize = bigSize
            }else {
                paint.color = white
                paint.textSize = smallSize
            }
            val y = centerY + (i- centerLine)* lineHeight
            canvas?.drawText(lyricBean.content, viewW/ 2.toFloat(), y, paint)
        }
    }

    private fun drawSingleText(canvas: Canvas?) {
        val str = "正在加载歌词..."
        //设置画笔属性
        paint.color = green
        paint.textSize = bigSize
        val bounds = Rect()
        //获取绘制的文字信息
        paint.getTextBounds(str, 0, str.length, bounds)
        //        val x = viewW/ 2 - bounds.width()/ 2  //可以不用设置
        val y = viewH / 2 + bounds.height() / 2
        canvas?.drawText(str, viewW / 2.toFloat(), y.toFloat(), paint)
    }

    /**
     * 更新歌词播放进度
     */
    fun updateProgress(progress: Int) {
        if (!updateByPro) return
        if (list.isEmpty()) return
        this.progress = progress
        if (progress >= list[list.size- 1].startTime) {
            //如果已经是最后一行
            centerLine = list.size - 1
        } else {
            //其他行
            for (i in 0 until list.size - 1) {
                val curTime = list[i].startTime
                val nextTime = list[i + 1].startTime
                if (progress in curTime until nextTime) {
                    centerLine = i
                    break
                }
            }
        }
        //重新绘制歌词
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewW = w
        viewH = h
    }

    /**
     * 设置歌曲名
     */
    fun setSongName(displayName: String) {
        doAsync {
            //异步添加数据
            val lyricFile = LyricLoader.loadLyricFile(displayName)
            val lyricList = LyricUtil.parseLyric(lyricFile)
            list.addAll(lyricList)
        }
    }

    /** 记录按下的Y */
    var downY = 0f
    /** 记录之前的偏移Y值 */
    var markY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    //当是按下的手势时，停止通过进度更新歌词
                    updateByPro = false
                    downY = it.y
                    markY = this.offsetY
                }
                MotionEvent.ACTION_UP -> {
                    updateByPro = true
                }
                MotionEvent.ACTION_MOVE -> {
                    val endY = it.y
                    val offsetY = downY - endY
                    //重新设置偏移量
                    this.offsetY = offsetY + markY
                    //居中行计算
                    if (Math.abs(offsetY) > lineHeight) {
                        //偏移大于行高
                        //计算偏移行数
                        val offsetLine = (offsetY / lineHeight).toInt()
                        centerLine += offsetLine
                        //边界处理
                        if (centerLine < 0) centerLine = 0 else if (centerLine > list.size - 1) centerLine = list.size -1
                        this.offsetY %= lineHeight
                        //重新记录按下的y值
                        downY = endY
                        markY = this.offsetY

                        //将数据回调回去
                        listener?.invoke(list[centerLine].startTime)
                    }
                    //重新绘制
                    invalidate()
                }
            }
        }
        return true
    }

    private var listener: ((progress: Int) -> Unit)? = null

    fun setProgressListener(listener: (progress: Int) -> Unit) {
        this.listener = listener
    }
}