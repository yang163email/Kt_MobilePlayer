package com.yan.mobile.player.service

import com.yan.mobile.player.enum.PlayMode
import com.yan.mobile.player.model.AudioBean

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/17 17:02
 *  @description : 通过 IService 接口，使Activity与Service进行通信
 */
interface IService {
    fun updatePlayState()
    fun isPlaying(): Boolean?
    fun getDuration(): Int
    fun getProgress(): Int
    fun seekTo(progress: Int)
    fun updatePlayMode()
    fun getPlayMode(): PlayMode
    fun playPre()
    fun playNext()
    fun getPlayList(): List<AudioBean>?
    fun playPosition(position: Int)
}