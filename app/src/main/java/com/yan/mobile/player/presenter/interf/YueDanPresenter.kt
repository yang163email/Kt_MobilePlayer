package com.yan.mobile.player.presenter.interf

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:35
 *  @description ：悦单界面presenter接口
 */
interface YueDanPresenter {
    fun loadData()
    fun loadMore(offset: Int)

    companion object {
        val TYPE_INIT_OR_REFRESH = 1
        val TYPE_LOAD_MORE = 2
    }
}