package com.yan.mobile.player.net

import com.itheima.player.model.bean.YueDanBean
import com.yan.mobile.player.utils.URLProviderUtils

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:39
 *  @description : 悦单解码inn请求
 */
class YueDanRequest(type: Int, offset: Int, handler: ResponseHandler<YueDanBean>) :
        MRequest<YueDanBean>(type, URLProviderUtils.getYueDanUrl(offset, 20), handler) {
}