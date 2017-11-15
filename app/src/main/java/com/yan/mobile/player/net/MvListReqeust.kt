package com.yan.mobile.player.net

import com.itheima.player.model.bean.MvPagerBean
import com.yan.mobile.player.utils.URLProviderUtils

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/15 10:32
 *  @description : mv列表数据请求
 */
class MvListReqeust(type: Int, code: String, offset: Int, handler: ResponseHandler<MvPagerBean>) :
        MRequest<MvPagerBean>(type, URLProviderUtils.getMVListUrl(code, offset, 20), handler) {
}