package com.yan.mobile.player.model

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 19:48
 *  @description ：HomeFragment数据bean
 */
data class HomeItemBean(
		var type: String?, //PROGRAM
		var subType: String?, //VIDEO
		var id: Int?, //3085670
		var title: String?, //狮子座的奇妙命理学 - 薛凯琪专访
		var description: String?, //STAR!调查团 & 薛凯琪
		var posterPic: String?, //http://img2.c.yinyuetai.com/others/mobile_front_page/171109/0/-M-d1e6af022ba44646e8ce955eb0b0c5ec_0x0.jpg
		var thumbnailPic: String?, //http://img1.yytcdn.com/video/mv/171109/0/-M-6c2739c8d0a5e0cd68fd9815c07ef842_240x135.jpg
		var url: String?, //http://hc.yinyuetai.com/uploads/videos/common/7CF9015F9EE666D73CB4BD5047863ED2.mp4?sc=5afc9d4b47ecfdf0&br=777&rd=Android
		var hdUrl: String?, //http://hc.yinyuetai.com/uploads/videos/common/7CF9015F9EE666D73CB4BD5047863ED2.mp4?sc=5afc9d4b47ecfdf0&br=777&rd=Android
		var uhdUrl: String?, //http://hd.yinyuetai.com/uploads/videos/common/9903015FA036416AB78FF13EE83DA44E.mp4?sc=b250391a4ccaceee&br=1096&rd=Android
		var videoSize: Int?, //0
		var hdVideoSize: Int?, //0
		var uhdVideoSize: Int?, //0
		var status: Int?, //200
		var traceUrl: String?, //http://www.yinyuetai.com/v?a=102437&un=53a621a9362eb7ed4e46425ac834f4b545fe1eff443acb1e2ba5fdc547da9314f66a78b03b640904a24e6f25376102b0c1dc16842b2b37e0d446aaffccd10a8cf69d2ebc6c2e79bfe31b925f005aee7e12ef159d573c37c97845c34d5e9dc329d8763c9a0e375997
		var clickUrl: String? //http://mapi.yinyuetai.com/statistics/click.json?id=6041
)