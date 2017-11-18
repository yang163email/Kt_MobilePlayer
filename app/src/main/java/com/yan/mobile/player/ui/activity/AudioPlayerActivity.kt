package com.yan.mobile.player.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.SeekBar
import com.yan.mobile.player.R
import com.yan.mobile.player.base.BaseActivity
import com.yan.mobile.player.model.AudioBean
import com.yan.mobile.player.service.AudioService
import com.yan.mobile.player.service.IService
import com.yan.mobile.player.utils.StringUtil
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.image

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/16 15:47
 *  @description : 音乐播放界面
 */
class AudioPlayerActivity: BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private val conn: AudioConnection by lazy { AudioConnection() }
    var iService: IService? = null
    var audioBean: AudioBean? = null
    var animDrawable: AnimationDrawable? = null
    var duration: Int = 0
    var MSG_UPDATE_PROGRESS = 0

    val handler = object :Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_UPDATE_PROGRESS -> startUpdateProgress()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initData() {
        //注册eventbus
        EventBus.getDefault().register(this)
        val intent = intent.setClass(this, AudioService::class.java)
        //先开启
        startService(intent)
        //绑定服务
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    override fun initListener() {
        state.setOnClickListener(this)
        back.setOnClickListener(this)
        progress_sk.setOnSeekBarChangeListener(this)
    }

    /**
     * 更新播放状态
     */
    private fun updatePlayState() {
        //更新状态
        iService?.updatePlayState()
        //改变图标
        updatePlayStateBtn()
    }

    private fun updatePlayStateBtn() {
        val isPlaying = iService?.isPlaying()
        isPlaying?.let {
            if (isPlaying) {
                //播放
                state.setImageResource(R.drawable.selector_btn_audio_play)
                //开始更新进度
                handler.sendEmptyMessage(MSG_UPDATE_PROGRESS)
                animDrawable?.start()
            } else{
                //暂停
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                //停止更新进度
                handler.removeMessages(MSG_UPDATE_PROGRESS)
                animDrawable?.stop()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.state -> updatePlayState()
            R.id.back -> finish()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAudioPrepared(audioBean: AudioBean) {
        this.audioBean = audioBean
        //标题，作者
        audio_title.text = audioBean.displayName
        artist.text = audioBean.artist
        //更新状态
        updatePlayStateBtn()
        //开始动画
        animDrawable = audio_anim.image as AnimationDrawable
        animDrawable?.start()

        //显示进度
        duration = iService?.getDuration()?:0
        progress_sk.max = duration
        //显示进度
        startUpdateProgress()
    }

    /**
     * 开始更新进度
     */
    private fun startUpdateProgress() {
        val progress: Int = iService?.getProgress()?:0
        //更新进度数据
        updateProgress(progress)
        //定时获取进度
        handler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 1000)
    }

    private fun updateProgress(pro: Int) {
        val currentPro = StringUtil.parseProgress(pro)
        val totalPro = StringUtil.parseProgress(duration)
        progress.text = currentPro + "/" + totalPro
        progress_sk.progress = pro
    }

    /**
     * seekbar进度发生变化的时候
     */
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (!fromUser) return   //如果不是通过拖动操作
        //更新播放进度
        iService?.seekTo(progress)
        //更新seekbar进度
        updateProgress(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    inner class AudioConnection: ServiceConnection {
        /**
         * 服务意外断开连接时
         */
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        /**
         * 服务连接时
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iService = service as IService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
        EventBus.getDefault().unregister(this)
        //清空handler发送的所有消息
        handler.removeCallbacksAndMessages(null)
    }
}