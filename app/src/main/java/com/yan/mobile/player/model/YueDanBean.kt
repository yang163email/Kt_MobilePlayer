package com.itheima.player.model.bean

/**
 * ClassName:YueDanBean
 * Description:
 */
class YueDanBean {

    var totalCount: Int = 0
    var playLists: List<PlayListsBean>? = null

    class PlayListsBean {

        var id: Int = 0
        var title: String? = null
        var thumbnailPic: String? = null
        var playListPic: String? = null
        var playListBigPic: String? = null
        var videoCount: Int = 0
        var description: String? = null
        var category: String? = null
        var creator: CreatorBean? = null
        var status: Int = 0
        var totalViews: Int = 0
        var totalFavorites: Int = 0
        var updateTime: String? = null
        var createdTime: String? = null
        var integral: Int = 0
        var weekIntegral: Int = 0
        var totalUser: Int = 0
        var rank: Int = 0

        class CreatorBean {

            var uid: Int = 0
            var nickName: String? = null
            var smallAvatar: String? = null
            var largeAvatar: String? = null
            var vipLevel: Int = 0
        }
    }
}
