package com.yan.mobile.player.ui.fragment

import android.view.View
import com.itheima.player.model.bean.MvAreaBean
import com.yan.mobile.player.R
import com.yan.mobile.player.adapter.MvPagerAdapter
import com.yan.mobile.player.base.BaseFragment
import com.yan.mobile.player.presenter.impl.MvPresenterImpl
import com.yan.mobile.player.view.IMvView
import kotlinx.android.synthetic.main.fragment_mv.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：mv界面
 */
class MvFragment : BaseFragment(), IMvView {
    private val mPresenter by lazy { MvPresenterImpl(this) }

    override fun initView(): View? {
        return layoutInflater.inflate(R.layout.fragment_mv, null)
    }

    override fun initListener() {

    }

    override fun initData() {
        mPresenter.loadData()
    }

    override fun onError(msg: String?) {
        myToast("加载数据事变")
    }

    override fun onSuccess(result: List<MvAreaBean>) {
        view_pager.adapter = MvPagerAdapter(context, childFragmentManager, result)
        tab_layout.setupWithViewPager(view_pager)
    }

}