package com.yan.mobile.player.utils

import com.yan.mobile.player.model.LyricBean
import java.io.File
import java.nio.charset.Charset

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/22 20:51
 *  @description : 歌词工具类
 */
object LyricUtil {

    /**
     * 解析歌词文件获取歌词集合
     */
    fun parseLyric(file: File): List<LyricBean> {
        val list = ArrayList<LyricBean>()
        if (!file.exists()) {
            //如果歌词文件不存在
            list.add(LyricBean(0, "未找到歌词文件"))
            return list
        }
        val linesList = file.readLines(Charset.forName("gbk"))
        linesList.forEach {
            //解析一行，添加到集合中
            val lineList: List<LyricBean> = parseLine(it)
            list.addAll(lineList)
        }
        //对集合进行排序
        list.sortBy { it.startTime }
        //返回集合
        return list
    }

    /**
     * 解析一行歌词
     */
    private fun parseLine(line: String): List<LyricBean> {
        val list = ArrayList<LyricBean>()
        //解析当前行
        val arr = line.split("]")
        //获取歌词内容
        val content = arr[arr.size - 1]
        for (index in 0 until arr.size - 1) {
            val startTime = parseTime(arr[index])
            list.add(LyricBean(startTime, content))
        }
        //返回集合
        return list
    }

    /**
     * 解析时间
     */
    private fun parseTime(s: String): Int {
        //去掉[
        val timeS = s.replace("[", "")
        //按照:切割
        var hour = 0
        var min = 0
        var sec = 0f
        val list = timeS.split(":")
        if (list.size == 3) {
            //有小时
            hour = list[0].toInt()* 60* 60* 1000
            min = list[1].toInt()* 60* 1000
            sec = list[2].toFloat()* 1000
        } else {
            min = list[0].toInt()* 60* 1000
            sec = list[1].toFloat()* 1000
        }
        return (hour + min + sec).toInt()
    }
}