package com.yan.mobile.player.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.yan.mobile.player.R
import com.yan.mobile.player.enum.PlayMode
import com.yan.mobile.player.model.AudioBean
import com.yan.mobile.player.ui.activity.AudioPlayerActivity
import com.yan.mobile.player.ui.activity.MainActivity
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/16 17:15
 *  @description : 音乐播放服务
 */
class AudioService: Service() {

    var list: ArrayList<AudioBean>? = null
    var position: Int = -2

    var mediaPlayer: MediaPlayer? = null
    var manager: NotificationManager? = null
    var notifycation: Notification? = null
    val binder by lazy { AudioBinder() }
    //播放模式
    var mode = PlayMode.MODE_ALL
    val sp by lazy { getSharedPreferences("config", Context.MODE_PRIVATE) }

    companion object {
        val REQ_PRE = 10
        val REQ_STATE = 11
        val REQ_NEXT = 12
        val REQ_CONTENT = 12

        val FROM_PRE = 1
        val FROM_STATE = 2
        val FROM_NEXT = 3
        val FROM_CONTENT = 4
    }

    override fun onCreate() {
        super.onCreate()
        //获取播放模式
        val curMode = sp.getInt("mode", 1)
        mode = PlayMode.values()[curMode]
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val pos = intent?.getIntExtra("position", -1)?:-1
        val from = intent?.getIntExtra("from", -1)
        when (from) {
            FROM_PRE -> { binder.playPre() }
            FROM_STATE -> { binder.updatePlayState() }
            FROM_NEXT -> { binder.playNext() }
            FROM_CONTENT -> { binder.notifyUpdateUI() }
            else -> {
                if (pos != position) {
                    //与上一次播放的不一致
                    //获取传递过来的数据
                    list = intent?.getParcelableArrayListExtra<AudioBean>("list")
                    position = pos
                    //开始播放音乐
                    binder.playItem()
                } else {
                    //一致,通知更新界面
                    binder.notifyUpdateUI()
                }
            }
        }

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

    inner class AudioBinder: Binder(), IService, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        /**
         * 播放指定条目的音乐
         */
        override fun playPosition(position: Int) {
            this@AudioService.position = position
            playItem()
        }

        /**
         * 获取播放列表数据
         */
        override fun getPlayList(): List<AudioBean>? {
            return list
        }

        /**
         * 播放上一曲
         */
        override fun playPre() {
            list?.let {
                when (mode) {
                    PlayMode.MODE_RANDOM -> position = Random().nextInt(it.size)
                    else -> {
                        //如果第一个，变成最后一个
                        if (position == 0) position = it.size - 1
                        else position--
                    }
                }
                playItem()
            }
        }

        /**
         * 播放下一曲
         */
        override fun playNext() {
            list?.let {
                position = when (mode) {
                    PlayMode.MODE_RANDOM -> Random().nextInt(it.size)
                    else -> (position + 1)% it.size
                }
                playItem()
            }
        }

        /**
         * 获取播放模式
         */
        override fun getPlayMode() = mode

        /**
         * 更新播放模式
         */
        override fun updatePlayMode() {
            mode = when(mode) {
                PlayMode.MODE_ALL -> PlayMode.MODE_SINGLE
                PlayMode.MODE_SINGLE -> PlayMode.MODE_RANDOM
                PlayMode.MODE_RANDOM -> PlayMode.MODE_ALL
            }
            //mode的序列
            val saveMode = mode.ordinal
            //保存mode
            sp.edit().putInt("mode", saveMode).commit()
        }

        override fun onCompletion(mp: MediaPlayer?) {
            autoPlayNext()
        }

        /**
         * 自动播放下一曲
         */
        private fun autoPlayNext() {
            list?.let {
                when (mode) {
                    PlayMode.MODE_ALL -> position = (position + 1)% it.size
                    PlayMode.MODE_RANDOM -> position = Random().nextInt(it.size)
                }
                playItem()
            }
        }

        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        override fun getDuration() = mediaPlayer?.duration?:0

        override fun getProgress() = mediaPlayer?.currentPosition?:0

        override fun isPlaying() = mediaPlayer?.isPlaying

        /**
         * 更新播放状态
         */
        override fun updatePlayState() {
            val isPlaying = isPlaying()
            isPlaying?.let {
                if (isPlaying) {
                    //播放状态 变暂停
                    pause()
                } else {
                    //暂停状态 变播放
                    start()
                }
            }
        }

        /** 播放 */
        private fun start() {
            mediaPlayer?.start()
            EventBus.getDefault().post(list?.get(position))
            //更新通知图标
            notifycation?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_play_normal)
            //重新显示
            manager?.notify(1, notifycation)
        }

        /** 暂停 */
        private fun pause() {
            mediaPlayer?.pause()
            EventBus.getDefault().post(list?.get(position))
            notifycation?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_pause_normal)
            //重新显示
            manager?.notify(1, notifycation)
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mp?.start()
            //通知界面更新
            notifyUpdateUI()
            //显示通知
            showNotification()
        }

        /**
         * 显示通知
         */
        private fun showNotification() {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifycation = getNotification()
            manager?.notify(1, notifycation)
        }

        private fun getNotification(): Notification? {
            val notification = NotificationCompat.Builder(this@AudioService)
                    .setTicker("正在播放歌曲${list?.get(position)?.displayName}")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setCustomContentView(getRemoteViews())
                    .setWhen(System.currentTimeMillis())    //设置通知栏时间
                    .setOngoing(true)   //设置通知不能被移除
                    .setContentIntent(getPendingIntent())
                    .build()
            return notification
        }

        /**
         * 点击通知主体事件
         */
        private fun getPendingIntent(): PendingIntent? {
            val intentM = Intent(this@AudioService, MainActivity::class.java)
            val intentA = Intent(this@AudioService, AudioPlayerActivity::class.java)
            intentA.putExtra("from", FROM_CONTENT)
            val intents = arrayOf(intentM, intentA)
            val pendingIntent = PendingIntent.getActivities(
                    this@AudioService, REQ_CONTENT, intents, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        private fun getRemoteViews(): RemoteViews? {
            val remoteViews = RemoteViews(packageName, R.layout.notification)
            remoteViews.setTextViewText(R.id.title, list?.get(position)?.displayName)
            remoteViews.setTextViewText(R.id.artist, list?.get(position)?.artist)
            remoteViews.setOnClickPendingIntent(R.id.pre, getPrePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.state, getStatePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.next, getNextPendingIntent())
            return remoteViews
        }

        /**
         * 点击上一曲事件
         */
        private fun getPrePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_PRE)
            val pendingIntent = PendingIntent.getService(
                    this@AudioService, REQ_PRE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 点击暂停、播放事件
         */
        private fun getStatePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_STATE)
            val pendingIntent = PendingIntent.getService(
                    this@AudioService, REQ_STATE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 点击下一曲事件
         */
        private fun getNextPendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_NEXT)
            val pendingIntent = PendingIntent.getService(
                    this@AudioService, REQ_NEXT, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        fun notifyUpdateUI() {
            //发送事件
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {
            if (mediaPlayer != null) {
                //先释放掉之前创建的mediaPlayer，避免多个音乐同时播放
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
            }
        }
    }
}