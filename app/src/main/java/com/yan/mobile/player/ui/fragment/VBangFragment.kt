package com.yan.mobile.player.ui.fragment

import android.Manifest
import android.content.AsyncQueryHandler
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.yan.mobile.player.R
import com.yan.mobile.player.adapter.VBangAdapter
import com.yan.mobile.player.base.BaseFragment
import com.yan.mobile.player.model.AudioBean
import com.yan.mobile.player.ui.activity.AudioPlayerActivity
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：v榜界面
 */
class VBangFragment : BaseFragment() {

    private var mAdapter: VBangAdapter? = null
    override fun initView(): View? {
        return layoutInflater.inflate(R.layout.fragment_vbang, null)
    }

    override fun initListener() {
        mAdapter = VBangAdapter(context, null)
        list_view.adapter = mAdapter

        list_view.setOnItemClickListener { parent, view, position, id ->
            val cursor = mAdapter?.getItem(position) as Cursor
            val list = AudioBean.getAudioBeans(cursor)
            startActivity<AudioPlayerActivity>("list" to list, "position" to position)
        }
    }

    override fun initData() {
    }

    private var isRequest = false
    override fun onResume() {
        super.onResume()
        if (!isRequest) {
            handlePermission()
            isRequest = true
        }
    }

    private fun handlePermission() {
        //申请权限
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val checkPermission = ContextCompat.checkSelfPermission(context, permission)
        if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            //已经获取到了权限
            loadSongs()
        } else {
            //没有获取到权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //需要弹出,点击了拒绝但是没有勾选不在询问
                alert("我们只会访问您的音乐文件，不会访问隐私图片", "温馨提示") {
                    yesButton { myRequestPermission() }
                    noButton {  }
                }.show()
            } else {
                //不要弹出，系统权限对话框
                myRequestPermission()
            }
        }
    }

    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }

    /**
     * 加载歌曲
     */
    private fun loadSongs() {
        val resolver = context.contentResolver

        val handler = object : AsyncQueryHandler(resolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                //查询完成的回调
                //打印数据
    //                CursorUtil.logCursor(cursor)
                //填充数据
                val adapter = cookie as VBangAdapter
                adapter.swapCursor(cursor)
            }
        }

        handler.startQuery(0, mAdapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Media._ID, //必须查询_id字段
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST),
                null, null, null)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myToast("成功授权")
                    isRequest = false
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                        myToast("没有权限，无法访问音乐文件")
                    } else {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:" + activity.packageName)
                        startActivity(intent)
                        isRequest = false
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //1.将adapter中的cursor关闭
        //2.将cursor置为null
        mAdapter?.changeCursor(null)
        isRequest = false
    }
}