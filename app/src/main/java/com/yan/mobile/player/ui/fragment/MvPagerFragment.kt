package com.yan.mobile.player.ui.fragment

import com.itheima.player.model.bean.MvPagerBean
import com.itheima.player.model.bean.VideosBean
import com.yan.mobile.player.adapter.MvListAdapter
import com.yan.mobile.player.base.BaseListAdapter
import com.yan.mobile.player.base.BaseListFragment
import com.yan.mobile.player.base.BaseListPresenter
import com.yan.mobile.player.model.VideoPlayerBean
import com.yan.mobile.player.presenter.impl.MvListPresenterImpl
import com.yan.mobile.player.ui.activity.JieCaoVideoPlayerActivity
import com.yan.mobile.player.view.IMvListView
import com.yan.mobile.player.widget.MvItemView
import org.jetbrains.anko.support.v4.startActivity

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 16:15
 *  @description : mv单页面Fragment
 */
class MvPagerFragment: BaseListFragment<MvPagerBean, VideosBean, MvItemView>(), IMvListView {
    lateinit var code: String
    override fun init() {
        code = arguments.getString("args")
    }

    override fun initListener() {
        super.initListener()
        mAdapter.setListener {
            val videoPlayerBean = VideoPlayerBean(it.id, it.title, it.url)
            startActivity<JieCaoVideoPlayerActivity>("item" to videoPlayerBean)
        }
    }
    override fun getAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code, this)
    }

    override fun getList(response: MvPagerBean?): List<VideosBean>? {
        return response?.videos
    }
}