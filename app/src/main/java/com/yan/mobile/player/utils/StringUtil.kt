package com.yan.mobile.player.utils

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/17 23:39
 *  @description : 处理时间
 */
object StringUtil {
    val HOUR = 60*60*1000
    val MIN = 60*1000
    val SEC = 1000

    /**
     * 将时间转换成字符串格式 00:00:00,不足一小时则为 00:00
     */
    fun parseProgress(progress: Int): String {
        val hour = progress / HOUR
        val min = progress % HOUR / MIN
        val sec = progress % MIN / SEC

        val result = if (hour == 0) {
            //不足一小时
            String.format("%02d:%02d", min, sec)
        } else{
            String.format("%02d:%02d:%02d", hour, min, sec)
        }
        return result
    }
}