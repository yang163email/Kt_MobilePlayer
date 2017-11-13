package com.yan.mobile.player.net

import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.utils.URLProviderUtils

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 14:19
 *  @description ：home界面请求
 */
class HomeRequest(type: Int, offset: Int, handler: ResponseHandler<List<HomeItemBean>>) :
        MRequest<List<HomeItemBean>>(type, URLProviderUtils.getHomeUrl(offset, 20), handler) {

}