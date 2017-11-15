package com.yan.mobile.player.presenter.impl

import com.yan.mobile.player.base.BaseListPresenter
import com.yan.mobile.player.base.IBaseView
import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.net.HomeRequest
import com.yan.mobile.player.net.ResponseHandler
import com.yan.mobile.player.presenter.interf.HomePresenter

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/12 22:02
 *  @description ：HomePresenter实现类
 */
class HomePresenterImpl(private var homeView: IBaseView<List<HomeItemBean>>?): HomePresenter, ResponseHandler<List<HomeItemBean>> {

    override fun destroyView() {
        if (homeView != null) homeView = null
    }

    override fun onSuccess(type: Int, result: List<HomeItemBean>) {
        //区分初始化、加载更多
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> homeView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> homeView?.loadMore(result)
        }
    }

    override fun onError(type: Int, msg: String?) {
        homeView?.onError(msg)
    }

    /**
     * 初始化数据、刷新数据
     */
    override fun loadData() {
        HomeRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        HomeRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()
    }
}