package com.example.moviedemo.screen.main.fragments.setting

import android.content.Context
import android.content.res.TypedArray
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

    }

    private var mYear = 2020
    fun getYear(): Int {
        return mYear
    }

    fun setYear(year: Int) {
        mYear = year
        if (this::txt_value.isInitialized)
            txt_value.text = year.toString()
        persistInt(year)
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? {
        // Default value from attribute. Fallback value is set to 0.
        return 2020
    }

    override fun onSetInitialValue(
        restorePersistedValue: Boolean,
        defaultValue: Any?
    ) {
        // Read the value. Use the default value if it is not possible.
        setYear(if (restorePersistedValue) getPersistedInt(mYear) else 2020)
    }

    override fun getDialogLayoutResource(): Int {
        return R.layout.yearpickerdialog
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        txt_value = holder!!.findViewById(R.id.txt_value) as TextView
        txt_value.text = getYear().toString()
        super.onBindViewHolder(holder)
    }


}