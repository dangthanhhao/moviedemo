package com.example.moviedemo.screen.main.fragments.setting

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.preference.PreferenceDialogFragmentCompat
import com.example.moviedemo.R


class DialogPrefFragCompat(val key: String, val initYear: Int) : PreferenceDialogFragmentCompat() {

    lateinit var yearpicker: NumberPicker

    init {
        val bundle = Bundle(1)
        bundle.putString(ARG_KEY, key)
        arguments = bundle

    }

    override fun onDialogClosed(positiveResult: Boolean) {

        if (positiveResult) {
            targetFragment!!.onActivityResult(
                targetRequestCode,
                Activity.RESULT_OK,
                requireActivity().intent.apply { putExtra("year", yearpicker.value) })

        }
    }


    override fun onBindDialogView(view: View?) {

        if (view != null) {

            yearpicker = view.findViewById<NumberPicker>(R.id.picker_year)
            yearpicker.minValue = 1998
            yearpicker.maxValue = 2020
            yearpicker.value = initYear
            yearpicker.wrapSelectorWheel = false


        }

        super.onBindDialogView(view)
    }

}