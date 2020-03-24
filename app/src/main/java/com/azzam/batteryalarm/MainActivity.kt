package com.azzam.batteryalarm

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CompoundButton
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.azzam.batteryalarm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var sharedPrefs: SharedPreferences

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addTextChangeListener()

        binding.alarmSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            alarmSwitchOnChange(buttonView, isChecked)
        }
    }

    private fun addTextChangeListener() {
        binding.percentageEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if(!editable.isNullOrEmpty()) {
                    if (editable.toString().toInt() !in 1..100) {
                        binding.percentageEditText.setText(100.toString())
                    }
                    updatePercentage(binding.percentageEditText.text.toString().toInt())
                } else {
                    updatePercentage(90) // Default value
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    private fun updatePercentage(value: Int){
        with(sharedPrefs.edit()){
            putInt(PERCENTAGE_KEY, value)
            commit()
        }
    }

    private fun alarmSwitchOnChange(view: CompoundButton, checked: Boolean) {
        // TODO: Disable/Enable the battery alarm
    }
}
