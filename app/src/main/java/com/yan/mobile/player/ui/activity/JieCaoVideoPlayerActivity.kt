package com.yan.mobile.player.ui.activity

import android.support.v4.view.ViewPager
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.yan.mobile.player.R
import com.yan.mobile.player.adapter.VideoPagerAdapter
import com.yan.mobile.player.base.BaseActivity
import com.yan.mobile.player.model.VideoPlayerBean
import kotlinx.android.synthetic.main.activity_video_player_jiecao.*




/**
 *  @author      : 楠GG
 *  @date        : 2017/11/15 11:37
 *  @description : 视频播放界面
 */
class JieCaoVideoPlayerActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_video_player_jiecao

    override fun initData() {
        val data = intent.data
        println("data=$data")
        if (data == null) {
            //应用内打开网络视频
            val videoPlayerBean = intent.getParcelableExtra<VideoPlayerBean>("item")
            videoplayer.setUp(videoPlayerBean.url,
                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, videoPlayerBean.title)
        } else {
            //应用外打开
            if (data.toString().startsWith("http")) {
                //应用外打开网络视频
                videoplayer.setUp(data.toString(),
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, data.path)
            } else {
                videoplayer.setUp(data.path,
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, data.path)
            }
        }

    }

    override fun initListener() {
        view_pager.adapter = VideoPagerAdapter(supportFragmentManager)
        //监听RadioButton的切换状态
        radio_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb1 -> view_pager.currentItem = 0
                R.id.rb2 -> view_pager.currentItem = 1
                R.id.rb3 -> view_pager.currentItem = 2
            }
        }
        //监听ViewPager的切换状态
        view_pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> radio_group.check(R.id.rb1)
                    1 -> radio_group.check(R.id.rb2)
                    2 -> radio_group.check(R.id.rb3)
                }
            }
        })
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }
}