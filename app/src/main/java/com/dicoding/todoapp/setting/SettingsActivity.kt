package com.dicoding.todoapp.setting

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dicoding.todoapp.R
import com.dicoding.todoapp.notification.NotificationWorker
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import com.dicoding.todoapp.utils.NOTIFICATION_CONTENT
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var mContext: Context
        private lateinit var workManager: WorkManager

        override fun onAttach(context: Context) {
            super.onAttach(context)
            mContext = context
            workManager = WorkManager.getInstance(context)
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
                val channelName = getString(R.string.notify_channel_name)
                val notifyContent = getString(R.string.notify_content)
                //TODO 13 : Schedule and cancel daily reminder using WorkManager with data channelName
                if(newValue.equals(true)){
                    val data = Data.Builder()
                        .putString(NOTIFICATION_CHANNEL_ID, channelName)
                        .putString(NOTIFICATION_CONTENT, notifyContent)
                        .build()

                    val periodicWorkRequest = PeriodicWorkRequest.Builder(
                        NotificationWorker::class.java,
                        1,
                        TimeUnit.DAYS
                    )
                        .addTag(channelName)
                        .setInputData(data)
                        .build()
                    workManager.enqueue(periodicWorkRequest)
                }else{
                    workManager.cancelAllWorkByTag(channelName)
                }
                true
            }

        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}