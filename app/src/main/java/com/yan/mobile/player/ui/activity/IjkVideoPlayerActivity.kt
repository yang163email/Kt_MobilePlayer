package com.yan.mobile.player.ui.activity

import com.yan.mobile.player.R
import com.yan.mobile.player.base.BaseActivity
import com.yan.mobile.player.model.VideoPlayerBean
import kotlinx.android.synthetic.main.activity_video_player_ijk.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/15 11:37
 *  @description : 视频播放界面
 */
class IjkVideoPlayerActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_video_player_ijk

    override fun initData() {
        val videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
        video_view.setVideoPath(videoPlayerBean.url)
        //视频加载好了再进行播放
        video_view.setOnPreparedListener {
            video_view.start()
        }
    }
}