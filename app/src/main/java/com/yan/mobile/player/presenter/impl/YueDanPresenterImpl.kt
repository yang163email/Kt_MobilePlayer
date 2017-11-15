package com.yan.mobile.player.presenter.impl

import com.itheima.player.model.bean.YueDanBean
import com.yan.mobile.player.base.BaseListPresenter
import com.yan.mobile.player.base.IBaseView
import com.yan.mobile.player.net.ResponseHandler
import com.yan.mobile.player.net.YueDanRequest
import com.yan.mobile.player.presenter.interf.YueDanPresenter

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:36
 *  @description ：悦单presenter实现类
 */
class YueDanPresenterImpl(private var yuedanView: IBaseView<YueDanBean>?): YueDanPresenter, ResponseHandler<YueDanBean> {
    override fun destroyView() {
        if (yuedanView != null) yuedanView = null
    }

    override fun onSuccess(type: Int, result: YueDanBean) {
        //初始获取、加载更多
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> yuedanView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> yuedanView?.loadMore(result)
        }
    }

    override fun onError(type: Int, msg: String?) {
        yuedanView?.onError(msg)
    }

    override fun loadData() {
        YueDanRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        YueDanRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()
    }

}