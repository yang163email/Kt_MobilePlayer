package com.yan.mobile.player.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yan.mobile.player.R
import com.yan.mobile.player.ui.activity.AboutActivity

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 15:47
 *  @description ：设置界面的Fragment
 */
class SettingFragment: PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        addPreferencesFromResource(R.xml.setting)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        val key = preference?.key
        if (key == "about") {
            activity.startActivity(Intent(activity, AboutActivity::class.java))
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}