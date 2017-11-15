package com.yan.mobile.player.presenter.impl

import com.itheima.player.model.bean.MvPagerBean
import com.yan.mobile.player.base.BaseListPresenter
import com.yan.mobile.player.net.MvListReqeust
import com.yan.mobile.player.net.ResponseHandler
import com.yan.mobile.player.presenter.interf.MvListPresenter
import com.yan.mobile.player.view.IMvListView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 18:03
 *  @description : mv列表presenter实现类
 */
class MvListPresenterImpl(private var code: String, private var iMvListView: IMvListView?): MvListPresenter, ResponseHandler<MvPagerBean> {
    override fun onSuccess(type: Int, result: MvPagerBean) {
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> iMvListView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> iMvListView?.loadMore(result)
        }
    }

    override fun onError(type: Int, msg: String?) {
        iMvListView?.onError(msg)
    }

    override fun loadData() {
        MvListReqeust(BaseListPresenter.TYPE_INIT_OR_REFRESH, code, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        MvListReqeust(BaseListPresenter.TYPE_LOAD_MORE, code, offset, this).execute()
    }

    override fun destroyView() {
        if (iMvListView != null) iMvListView = null
    }
}