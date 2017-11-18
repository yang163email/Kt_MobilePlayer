package com.yan.mobile.player.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.yan.mobile.player.model.AudioBean
import org.greenrobot.eventbus.EventBus

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/16 17:15
 *  @description : 音乐播放服务
 */
class AudioService: Service() {

    var list: ArrayList<AudioBean>? = null
    var position: Int = 0

    var mediaPlayer: MediaPlayer? = null
    val binder by lazy { AudioBinder() }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //获取传递过来的数据
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position = intent?.getIntExtra("position", -1)?:-1

        //开始播放音乐
        binder.playItem(list, position)

        // START_STICKY: service强制杀死之后，会尝试重新启动， 不会传递原来的intent
        // START_NOT_STICKY: service强制杀死之后，不会重新启动
        // START_REDELIVER_INTENT: service强制杀死之后，会尝试重新启动，会传递原来的intent，但对国产手机无效
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun isPlayeing() = mediaPlayer?.isPlaying

    inner class AudioBinder: Binder(), IService, MediaPlayer.OnPreparedListener {
        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        override fun getDuration() = mediaPlayer?.duration?:0

        override fun getProgress() = mediaPlayer?.currentPosition?:0

        override fun isPlaying() = mediaPlayer?.isPlaying

        override fun updatePlayState() {
            val isPlaying = isPlayeing()
            isPlaying?.let {
                if (isPlaying) {
                    //播放状态 变暂停
                    mediaPlayer?.pause()
                } else {
                    //暂停状态 变播放
                    mediaPlayer?.start()
                }
            }
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mp?.start()
            //通知界面更新
            notifyUpdateUI()
        }

        private fun notifyUpdateUI() {
            //发送事件
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem(list: ArrayList<AudioBean>?, position: Int) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
                it.setOnPreparedListener(this)
            }
        }
    }
}