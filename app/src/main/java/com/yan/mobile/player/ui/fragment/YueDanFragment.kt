package com.yan.mobile.player.ui.fragment

import com.itheima.player.model.bean.YueDanBean
import com.yan.mobile.player.adapter.YueDanAdapter
import com.yan.mobile.player.base.BaseListAdapter
import com.yan.mobile.player.base.BaseListFragment
import com.yan.mobile.player.base.BaseListPresenter
import com.yan.mobile.player.presenter.impl.YueDanPresenterImpl
import com.yan.mobile.player.widget.YueDanItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：悦单界面
 */
class YueDanFragment: BaseListFragment<YueDanBean, YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun getAdapter(): BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun getPresenter(): BaseListPresenter {
        return YueDanPresenterImpl(this)
    }

    override fun getList(response: YueDanBean?): List<YueDanBean.PlayListsBean>? {
        return response?.playLists
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.destroyView()
    }
}