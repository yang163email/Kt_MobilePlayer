package com.yan.mobile.player.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.yan.mobile.player.R
import com.yan.mobile.player.adapter.PopAdapter
import com.yan.mobile.player.base.BaseActivity
import com.yan.mobile.player.enum.PlayMode
import com.yan.mobile.player.model.AudioBean
import com.yan.mobile.player.service.AudioService
import com.yan.mobile.player.service.IService
import com.yan.mobile.player.utils.StringUtil
import com.yan.mobile.player.widget.PlayListPopupWindow
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
class AudioPlayerActivity: BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        iService?.playPosition(position)
    }

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
        //先绑定服务
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
        //再开启
        startService(intent)
    }

    override fun initListener() {
        state.setOnClickListener(this)
        back.setOnClickListener(this)
        progress_sk.setOnSeekBarChangeListener(this)
        mode.setOnClickListener(this)
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        playlist.setOnClickListener(this)
        lyric_view.setProgressListener {
            //歌词进度更新
            //更新播放进度
            iService?.seekTo(it)
            //进度显示更新
            updateProgress(it)
        }
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
            R.id.mode -> updatePlayMode()
            R.id.pre -> iService?.playPre()
            R.id.next -> iService?.playNext()
            R.id.playlist -> showPlayList()
        }
    }

    /**
     * 显示播放列表
     */
    private fun showPlayList() {
        val list = iService?.getPlayList()
        list?.let {
            val adapter = PopAdapter(list)
            val popupWindow = PlayListPopupWindow(this, adapter, this, window)
            val yoff = audio_player_bottom.height
            popupWindow.showAsDropDown(audio_player_bottom, 0, -yoff)
        }
    }

    /**
     * 更新播放模式
     */
    private fun updatePlayMode() {
        //Service中更新
        iService?.updatePlayMode()
        //界面图片更新
        updatePlayModeBtn()
    }

    /**
     * 更新播放模式
     */
    private fun updatePlayModeBtn() {
        iService?.let {
            //获取播放模式
            val playMode: PlayMode = it.getPlayMode()
            val resId = when(playMode) {
                PlayMode.MODE_ALL -> R.drawable.selector_btn_playmode_order
                PlayMode.MODE_SINGLE -> R.drawable.selector_btn_playmode_single
                PlayMode.MODE_RANDOM -> R.drawable.selector_btn_playmode_random
            }
            mode.setImageResource(resId)
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
        //设置歌词总进度
        lyric_view.duration = duration
        //设置歌词
        lyric_view.setSongName(audioBean.displayName)
        progress_sk.max = duration
        //显示进度
        startUpdateProgress()
        //显示播放模式图标
        updatePlayModeBtn()
    }

    /**
     * 开始更新进度
     */
    private fun startUpdateProgress() {
        val progress: Int = iService?.getProgress()?:0
        //更新进度数据
        updateProgress(progress)
        //定时获取进度
        handler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 1)
    }

    private fun updateProgress(pro: Int) {
        val currentPro = StringUtil.parseProgress(pro)
        val totalPro = StringUtil.parseProgress(duration)
        progress.text = currentPro + "/" + totalPro
        progress_sk.progress = pro
        //更新歌词
        lyric_view.updateProgress(pro)
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