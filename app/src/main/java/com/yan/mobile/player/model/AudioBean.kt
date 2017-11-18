package com.yan.mobile.player.model

import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/16 12:53
 *  @description : 音乐列表对应的bean
 */
data class AudioBean(var data: String, var size: Long, var displayName: String, var artist: String)
    : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
        parcel.writeLong(size)
        parcel.writeString(displayName)
        parcel.writeString(artist)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AudioBean> {
        override fun createFromParcel(parcel: Parcel): AudioBean {
            return AudioBean(parcel)
        }

        override fun newArray(size: Int): Array<AudioBean?> {
            return arrayOfNulls(size)
        }

        fun getAudioBean(cursor: Cursor?): AudioBean {
            val audioBean = AudioBean("", 0, "", "")
            cursor?.let {
                audioBean.data = it.getString(it.getColumnIndex(MediaStore.Audio.Media.DATA))
                audioBean.size = it.getLong(it.getColumnIndex(MediaStore.Audio.Media.SIZE))
                audioBean.displayName = it.getString(it.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                audioBean.displayName = audioBean.displayName.substring(0, audioBean.displayName.lastIndexOf("."))
                audioBean.artist = it.getString(it.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            }
            return audioBean
        }

        fun getAudioBeans(cursor: Cursor?): ArrayList<AudioBean> {
            val list = ArrayList<AudioBean>()
            cursor?.let {
                it.moveToPosition(-1)
                while (it.moveToNext()) {
                    val audioBean = getAudioBean(it)
                    list.add(audioBean)
                }
            }
            return list
        }
    }
}