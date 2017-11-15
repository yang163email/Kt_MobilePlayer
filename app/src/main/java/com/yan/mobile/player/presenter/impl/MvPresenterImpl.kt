package com.yan.mobile.player.presenter.impl

import com.itheima.player.model.bean.MvAreaBean
import com.yan.mobile.player.net.MvAreaRequest
import com.yan.mobile.player.net.ResponseHandler
import com.yan.mobile.player.presenter.interf.MvPresenter
import com.yan.mobile.player.view.IMvView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 15:51
 *  @description : mv界面presenter实现类
 */
class MvPresenterImpl(var mvView: IMvView): MvPresenter, ResponseHandler<List<MvAreaBean>> {
    override fun onSuccess(type: Int, result: List<MvAreaBean>) {
        mvView.onSuccess(result)
    }

    override fun onError(type: Int, msg: String?) {
        mvView.onError(msg)
    }

    override fun loadData() {
        MvAreaRequest(this).execute()
    }
}