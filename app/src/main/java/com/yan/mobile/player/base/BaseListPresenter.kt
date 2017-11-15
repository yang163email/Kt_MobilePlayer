package com.yan.mobile.player.base

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 14:46
 *  @description : 所有带有下拉刷新，上拉加载更多的presenter
 */
interface BaseListPresenter {

    fun loadData()

    fun loadMore(offset: Int)

    companion object {
        val TYPE_INIT_OR_REFRESH = 1
        val TYPE_LOAD_MORE = 2
    }

    /**
     * 解绑presenter与view
     */
    fun destroyView()
}