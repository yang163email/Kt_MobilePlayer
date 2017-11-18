package com.yan.mobile.player.service

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/17 17:02
 *  @description : TODO
 */
interface IService {
    fun updatePlayState()
    fun isPlaying(): Boolean?
    fun getDuration(): Int
    fun getProgress(): Int
    fun seekTo(progress: Int)
}