package com.stockpenguin.materialcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class SettingsActivity : AppCompatActivity() {
    private lateinit var themeManager: ThemeManager

    private lateinit var settingsLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        themeManager = ThemeManager(this)
        settingsLayout = findViewById(R.id.settingsLayout_SettingsActivity)

        settingsLayout.setBackgroundColor(themeManager.currentThemePrimary)
    }
}