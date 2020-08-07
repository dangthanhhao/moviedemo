package com.example.moviedemo.screen.main.fragments.setting

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.DialogPreference
import androidx.preference.PreferenceViewHolder
import com.example.moviedemo.R


class YearPickerDialogPreference(context: Context, attributes: AttributeSet) :
    DialogPreference(context, attributes) {
    lateinit var txt_value: TextView

    init {
        widgetLayoutResource = R.layout.year_preference
        dialogTitle = null
    }

    var mYear = 2017 //default year
    fun getYear(): Int {
        return mYear
    }

    fun setYear(year: Int) {
        mYear = year
        if (this::txt_value.isInitialized)
            txt_value.text = year.toString()
        persistInt(year)
    }


    override fun onSetInitialValue(
        restorePersistedValue: Boolean,
        defaultValue: Any?
    ) {
        // Read the value. Use the default value if it is not possible.
        setYear(if (restorePersistedValue) getPersistedInt(mYear) else defaultValue as Int)
    }

    override fun getDialogLayoutResource(): Int {
        //layout for DialogPreFragCompat
        return R.layout.yearpickerdialog
        // return super.getDialogLayoutResource()
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        txt_value = holder!!.findViewById(R.id.txt_value) as TextView
        txt_value.text = getYear().toString()
        super.onBindViewHolder(holder)
    }


}