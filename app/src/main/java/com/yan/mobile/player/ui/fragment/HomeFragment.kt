package com.yan.mobile.player.ui.fragment

import com.yan.mobile.player.adapter.HomeAdapter
import com.yan.mobile.player.base.BaseListAdapter
import com.yan.mobile.player.base.BaseListFragment
import com.yan.mobile.player.base.BaseListPresenter
import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.presenter.impl.HomePresenterImpl
import com.yan.mobile.player.widget.HomeItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：首页Fragment
 */
class HomeFragment: BaseListFragment<List<HomeItemBean>, HomeItemBean, HomeItemView>(){
    override fun getAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }

    override fun getPresenter(): BaseListPresenter {
        return HomePresenterImpl(this)
    }

    override fun getList(response: List<HomeItemBean>?): List<HomeItemBean>? {
        return response
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.destroyView()
    }
}