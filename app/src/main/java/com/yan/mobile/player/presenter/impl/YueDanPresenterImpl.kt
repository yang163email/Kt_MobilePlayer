package com.yan.mobile.player.presenter.impl

import com.itheima.player.model.bean.YueDanBean
import com.yan.mobile.player.net.ResponseHandler
import com.yan.mobile.player.net.YueDanRequest
import com.yan.mobile.player.presenter.interf.YueDanPresenter
import com.yan.mobile.player.view.IYueDanView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:36
 *  @description ：悦单presenter实现类
 */
class YueDanPresenterImpl(var yuedanView: IYueDanView): YueDanPresenter, ResponseHandler<YueDanBean> {
    override fun onSuccess(type: Int, result: YueDanBean) {
        //初始获取、加载更多
        when (type) {
            YueDanPresenter.TYPE_INIT_OR_REFRESH -> yuedanView.loadSuccess(result)
            YueDanPresenter.TYPE_LOAD_MORE -> yuedanView.loadMore(result)
        }
    }

    override fun onError(type: Int, msg: String?) {
        yuedanView.onError(msg)
    }

    override fun loadData() {
        YueDanRequest(YueDanPresenter.TYPE_INIT_OR_REFRESH, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        YueDanRequest(YueDanPresenter.TYPE_LOAD_MORE, offset, this).execute()
    }

}