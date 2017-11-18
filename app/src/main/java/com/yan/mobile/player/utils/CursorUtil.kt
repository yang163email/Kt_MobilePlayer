package com.yan.mobile.player.utils

import android.database.Cursor

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/15 19:55
 *  @description : cursor打印工具类
 */
object CursorUtil {

    fun logCursor(cursor: Cursor?) {
        cursor?.let {
            cursor.moveToPosition(-1)
            while (cursor.moveToNext()) {
                for (index in 0 until cursor.columnCount) {
                    println("key=${cursor.getColumnName(index)} --value=${cursor.getString(index)}")
                }
            }
        }
    }
}