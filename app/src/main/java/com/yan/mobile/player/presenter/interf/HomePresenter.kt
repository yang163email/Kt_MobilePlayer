package com.yan.mobile.player.presenter.interf

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/12 22:01
 *  @description ：TODO
 */
interface HomePresenter {
    fun loadData()
    fun loadMore(offset: Int)

    companion object {
        val TYPE_INIT_OR_REFRESH = 1
        val TYPE_LOAD_MORE = 2
    }
}