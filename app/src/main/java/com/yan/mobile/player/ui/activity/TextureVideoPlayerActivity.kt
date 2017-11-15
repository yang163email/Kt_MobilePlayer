package com.yan.mobile.player.ui.activity

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import com.yan.mobile.player.R
import com.yan.mobile.player.base.BaseActivity
import com.yan.mobile.player.model.VideoPlayerBean
import kotlinx.android.synthetic.main.activity_video_player_texture.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/15 11:37
 *  @description : 视频播放界面
 */
class TextureVideoPlayerActivity : BaseActivity(), TextureView.SurfaceTextureListener {

    var videoPlayerBean: VideoPlayerBean? = null

    val mediaPlayer by lazy { MediaPlayer() }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        //视图更新
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        //页面关闭时，取消播放
        mediaPlayer?.let {
            it.stop()
            it.release()
        }
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        //可用时
        videoPlayerBean?.let {
            mediaPlayer.setDataSource(it.url)
            mediaPlayer.setSurface(Surface(surface))    //设置播放视频画面
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                //旋转操作
                texture_view.rotation = 90f
            }
        }

    }

    override fun getLayoutId() = R.layout.activity_video_player_texture

    override fun initData() {
        videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
        texture_view.surfaceTextureListener = this
    }
}