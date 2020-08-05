package com.example.moviedemo.screen.main.fragments.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.moviedemo.R

const val REQUEST_YEAR_FILTER = 1998

class SettingFragment : PreferenceFragmentCompat() {
    lateinit var listFilter: MutableList<CheckBoxPreference>
    lateinit var listSorter: MutableList<CheckBoxPreference>
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        initFilterMoviesGroup()
        initSorterMoviesGroup()

    }

    private fun initSorterMoviesGroup() {
        listSorter = mutableListOf(
            findPreference("release_date_sorter")!!,
            findPreference("rating_sorter")!!
        )
        listSorter.forEach { item ->
            item.setOnPreferenceClickListener {
                listSorter.forEach {
                    it.isChecked = false
                }
                item.isChecked = true
                true
            }
        }

    }

    private fun initFilterMoviesGroup() {
        listFilter = mutableListOf(
            findPreference("popular_movies")!!,
            findPreference("top_rates_movies")!!,
            findPreference("upcoming_movies")!!,
            findPreference("now_playing_movies")!!
        )
        listFilter.forEach { item ->
            item.setOnPreferenceClickListener {
                listFilter.forEach {
                    it.isChecked = false
                }
                (it as CheckBoxPreference).isChecked = true
                true
            }
        }
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is YearPickerDialogPreference) {

            val dialogFragment = DialogPrefFragCompat(preference.key, preference.getYear())
            dialogFragment.setTargetFragment(this, REQUEST_YEAR_FILTER)

            dialogFragment.show(requireFragmentManager(), null)
        } else super.onDisplayPreferenceDialog(preference)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_YEAR_FILTER && resultCode == Activity.RESULT_OK) {
            data?.extras?.getInt("year")?.let {
                findPreference<YearPickerDialogPreference>("year_filter")?.setYear(
                    it
                )
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}