package com.yan.mobile.player.utils

import android.os.Environment
import java.io.File

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/22 21:11
 *  @description : 歌词文件加载util
 */
object LyricLoader {
    val dir = File(Environment.getExternalStorageDirectory(), "Download/Lyric")

    /**
     * 根据歌曲名，获取歌词文件
     */
    fun loadLyricFile(displayName: String): File {
        return File(dir, displayName + ".lrc")
    }

}