package com.yan.mobile.player.net

import com.itheima.player.model.bean.MvAreaBean
import com.yan.mobile.player.utils.URLProviderUtils

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 15:51
 *  @description : TODO
 */
class MvAreaRequest(handler: ResponseHandler<List<MvAreaBean>>) :
        MRequest<List<MvAreaBean>>(0, URLProviderUtils.getMVareaUrl(), handler) {
}