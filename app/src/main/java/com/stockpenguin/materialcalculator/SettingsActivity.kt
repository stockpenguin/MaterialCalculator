package com.stockpenguin.materialcalculator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener


class SettingsActivity : AppCompatActivity() {
    private lateinit var themeManager: ThemeManager

    private lateinit var settingsLayout: ConstraintLayout
    private lateinit var toolbar: Toolbar

    private lateinit var themesCardView: CardView
    private lateinit var lightPurpleCardView: CardView
    private lateinit var darkPurpleCardView: CardView
    private lateinit var lightNormalCardView: CardView
    private lateinit var darkNormalCardView: CardView
    private lateinit var lightRedCardView: CardView
    private lateinit var darkRedCardView: CardView

    private lateinit var lightPurpleRadioButton: RadioButton
    private lateinit var darkPurpleRadioButton: RadioButton
    private lateinit var lightNormalRadioButton: RadioButton
    private lateinit var darkNormalRadioButton: RadioButton
    private lateinit var lightRedRadioButton: RadioButton
    private lateinit var darkRedRadioButton: RadioButton

    private lateinit var radioButtonList: List<RadioButton>

    private lateinit var lightPurpleLock: ImageView
    private lateinit var darkPurpleLock: ImageView
    private lateinit var lightNormalLock: ImageView
    private lateinit var darkNormalLock: ImageView
    private lateinit var lightRedLock: ImageView
    private lateinit var darkRedLock: ImageView

    private var lightPurpleLocked = true
    private var darkPurpleLocked = false
    private var lightNormalLocked = true
    private var darkNormalLocked = true
    private var lightRedLocked = true
    private var darkRedLocked = true

    private val LOCKED_THEMES = "LOCKED_THEMES"

    private val LIGHT_PURPLE_LOCKED = "LIGHT_PURPLE_LOCKED"
    private val DARK_PURPLE_LOCKED = "DARK_PURPLE_LOCKED"
    private val LIGHT_NORMAL_LOCKED = "LIGHT_NORMAL_LOCKED"
    private val DARK_NORMAL_LOCKED = "DARK_NORMAL_LOCKED"
    private val LIGHT_RED_LOCKED = "LIGHT_RED_LOCKED"
    private val DARK_RED_LOCKED = "DARK_RED_LOCKED"

    private var themeToUnlock: Theme? = null

    private lateinit var rewardedVideoAd: RewardedVideoAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        init()
    }

    private fun initAdMob() {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)

        rewardedVideoAd.rewardedVideoAdListener = object: RewardedVideoAdListener {
            override fun onRewardedVideoAdLoaded() {
                rewardedVideoAd.show()
            }

            override fun onRewardedVideoAdOpened() {}

            override fun onRewardedVideoStarted() {}

            override fun onRewardedVideoAdClosed() {}

            override fun onRewarded(p0: com.google.android.gms.ads.reward.RewardItem?) {}

            override fun onRewardedVideoAdLeftApplication() {}

            override fun onRewardedVideoAdFailedToLoad(p0: Int) {}

            override fun onRewardedVideoCompleted() {
                themeToUnlock?.let { unlockTheme(it) }
                themeToUnlock = null
            }
        }
    }

    private fun init() {
        initAdMob()

        settingsLayout = findViewById(R.id.settingsLayout_SettingsActivity)
        toolbar = findViewById(R.id.toolbar_SettingsActivity)

        themesCardView = findViewById(R.id.themesCardView_SettingsActivity)

        lightPurpleCardView = findViewById(R.id.lightPurpleCardView_SettingsActivity)
        darkPurpleCardView = findViewById(R.id.darkPurpleCardView_SettingsActivity)
        lightNormalCardView = findViewById(R.id.lightNormalCardView_SettingsActivity)
        darkNormalCardView = findViewById(R.id.darkNormalCardView_SettingsActivity)
        lightRedCardView = findViewById(R.id.lightRedCardView_SettingsActivity)
        darkRedCardView = findViewById(R.id.darkRedCardView_SettingsActivity)

        lightPurpleCardView.setOnClickListener { v -> unlockTheme(v) }
        darkPurpleCardView.setOnClickListener { v -> unlockTheme(v) }
        lightNormalCardView.setOnClickListener { v -> unlockTheme(v) }
        darkNormalCardView.setOnClickListener { v -> unlockTheme(v) }
        lightRedCardView.setOnClickListener { v -> unlockTheme(v) }
        darkRedCardView.setOnClickListener { v -> unlockTheme(v) }

        lightPurpleRadioButton = findViewById(R.id.lightPurpleRadioButton_SettingsActivity)
        darkPurpleRadioButton = findViewById(R.id.darkPurpleRadioButton_SettingsActivity)
        lightNormalRadioButton = findViewById(R.id.lightNormalRadioButton_SettingsActivity)
        darkNormalRadioButton = findViewById(R.id.darkNormalRadioButton_SettingsActivity)
        lightRedRadioButton = findViewById(R.id.lightRedRadioButton_SettingsActivity)
        darkRedRadioButton = findViewById(R.id.darkRedRadioButton_SettingsActivity)

        radioButtonList = listOf(
            lightPurpleRadioButton,
            darkPurpleRadioButton,
            lightNormalRadioButton,
            darkNormalRadioButton,
            lightRedRadioButton,
            darkRedRadioButton
        )

        lightPurpleRadioButton.setOnClickListener { v -> updateRadioButtons(v) }
        darkPurpleRadioButton.setOnClickListener { v -> updateRadioButtons(v) }
        lightNormalRadioButton.setOnClickListener { v -> updateRadioButtons(v) }
        darkNormalRadioButton.setOnClickListener { v -> updateRadioButtons(v) }
        lightRedRadioButton.setOnClickListener { v -> updateRadioButtons(v) }
        darkRedRadioButton.setOnClickListener { v -> updateRadioButtons(v) }

        lightPurpleLock = findViewById(R.id.lightPurpleLockImage_SettingsActivity)
        darkPurpleLock = findViewById(R.id.darkPurpleLockImage_SettingsActivity)
        lightNormalLock = findViewById(R.id.lightNormalLockImage_SettingsActivity)
        darkNormalLock = findViewById(R.id.darkNormalLockImage_SettingsActivity)
        lightRedLock = findViewById(R.id.lightRedLockImage_SettingsActivity)
        darkRedLock = findViewById(R.id.darkRedLockImage_SettingsActivity)

        themeManager = ThemeManager(this)

        updateUI()

        selectCurrentRadioButton()
        checkLockedThemes()
    }

    private fun selectCurrentRadioButton() {
        when (themeManager.currentTheme) {
            Theme.LIGHT_PURPLE -> lightPurpleRadioButton.isChecked = true
            Theme.DARK_PURPLE -> darkPurpleRadioButton.isChecked = true
            Theme.LIGHT_NORMAL -> lightNormalRadioButton.isChecked = true
            Theme.DARK_NORMAL -> darkNormalRadioButton.isChecked = true
            Theme.LIGHT_RED -> lightRedRadioButton.isChecked = true
            Theme.DARK_RED -> darkRedRadioButton.isChecked = true
            null -> {}
        }
    }

    private fun updateRadioButtons(v: View) {
        v as RadioButton
        for (radioButton in radioButtonList) radioButton.isChecked = false
        v.isChecked = true

        when (v.id) {
            R.id.lightPurpleRadioButton_SettingsActivity -> themeManager.currentTheme = Theme.LIGHT_PURPLE
            R.id.darkPurpleRadioButton_SettingsActivity -> themeManager.currentTheme = Theme.DARK_PURPLE
            R.id.lightNormalRadioButton_SettingsActivity -> themeManager.currentTheme = Theme.LIGHT_NORMAL
            R.id.darkNormalRadioButton_SettingsActivity -> themeManager.currentTheme = Theme.DARK_NORMAL
            R.id.lightRedRadioButton_SettingsActivity -> themeManager.currentTheme = Theme.LIGHT_RED
            R.id.darkRedRadioButton_SettingsActivity -> themeManager.currentTheme = Theme.DARK_RED
        }

        updateUI()
    }

    // test: ca-app-pub-3940256099942544/5224354917

    private fun unlockTheme(v: View) {
        when (v.id) {
            R.id.lightPurpleCardView_SettingsActivity -> {
                if (lightPurpleLocked) {
                    themeToUnlock = Theme.LIGHT_PURPLE
                    rewardedVideoAd.loadAd("ca-app-pub-5232085909273792/9974879906", AdRequest.Builder().build())
                }
            }
            R.id.darkPurpleCardView_SettingsActivity -> {
                if (darkPurpleLocked) {
                    themeToUnlock = Theme.DARK_PURPLE
                    rewardedVideoAd.loadAd("ca-app-pub-5232085909273792/9974879906", AdRequest.Builder().build())
                }
            }
            R.id.lightNormalCardView_SettingsActivity -> {
                if (lightNormalLocked) {
                    themeToUnlock = Theme.LIGHT_NORMAL
                    rewardedVideoAd.loadAd("ca-app-pub-5232085909273792/9974879906", AdRequest.Builder().build())
                }
            }
            R.id.darkNormalCardView_SettingsActivity -> {
                if (darkNormalLocked) {
                    themeToUnlock = Theme.DARK_NORMAL
                    rewardedVideoAd.loadAd("ca-app-pub-5232085909273792/9974879906", AdRequest.Builder().build())
                }
            }
            R.id.lightRedCardView_SettingsActivity -> {
                if (lightRedLocked) {
                    themeToUnlock = Theme.LIGHT_RED
                    rewardedVideoAd.loadAd("ca-app-pub-5232085909273792/9974879906", AdRequest.Builder().build())
                }
            }
            R.id.darkRedCardView_SettingsActivity -> {
                if (darkRedLocked) {
                    themeToUnlock = Theme.DARK_RED
                    rewardedVideoAd.loadAd("ca-app-pub-5232085909273792/9974879906", AdRequest.Builder().build())
                }
            }
        }
    }

    private fun unlockTheme(t: Theme) {
        val sp = getSharedPreferences(LOCKED_THEMES, Context.MODE_PRIVATE)
        val e = sp.edit()
        when (t) {
            Theme.LIGHT_PURPLE -> e.putBoolean(LIGHT_PURPLE_LOCKED, false).apply()
            Theme.DARK_PURPLE -> e.putBoolean(DARK_PURPLE_LOCKED, false).apply()
            Theme.LIGHT_NORMAL -> e.putBoolean(LIGHT_NORMAL_LOCKED, false).apply()
            Theme.DARK_NORMAL -> e.putBoolean(DARK_NORMAL_LOCKED, false).apply()
            Theme.LIGHT_RED -> e.putBoolean(LIGHT_RED_LOCKED, false).apply()
            Theme.DARK_RED -> e.putBoolean(DARK_RED_LOCKED, false).apply()
        }

        checkLockedThemes()
    }

    private fun checkLockedThemes() {
        val sp = getSharedPreferences(LOCKED_THEMES, Context.MODE_PRIVATE)

        lightPurpleLocked = sp.getBoolean(LIGHT_PURPLE_LOCKED, true)
        darkPurpleLocked = sp.getBoolean(DARK_PURPLE_LOCKED, false)
        lightNormalLocked = sp.getBoolean(LIGHT_NORMAL_LOCKED, true)
        darkNormalLocked = sp.getBoolean(DARK_NORMAL_LOCKED, true)
        lightRedLocked = sp.getBoolean(LIGHT_RED_LOCKED, true)
        darkRedLocked = sp.getBoolean(DARK_RED_LOCKED, true)

        if (lightPurpleLocked) {
            lightPurpleRadioButton.isEnabled = false
            lightPurpleRadioButton.visibility = View.INVISIBLE
            lightPurpleLock.visibility = View.VISIBLE
        } else {
            lightPurpleRadioButton.isEnabled = true
            lightPurpleRadioButton.visibility = View.VISIBLE
            lightPurpleLock.visibility = View.INVISIBLE
        }

        if (darkPurpleLocked) {
            darkPurpleRadioButton.isEnabled = false
            darkPurpleRadioButton.visibility = View.INVISIBLE
            darkPurpleLock.visibility = View.VISIBLE
        } else {
            darkPurpleRadioButton.isEnabled = true
            darkPurpleRadioButton.visibility = View.VISIBLE
            darkPurpleLock.visibility = View.INVISIBLE
        }

        if (lightNormalLocked) {
            lightNormalRadioButton.isEnabled = false
            lightNormalRadioButton.visibility = View.INVISIBLE
            lightNormalLock.visibility = View.VISIBLE
        } else {
            lightNormalRadioButton.isEnabled = true
            lightNormalRadioButton.visibility = View.VISIBLE
            lightNormalLock.visibility = View.INVISIBLE
        }

        if (darkNormalLocked) {
            darkNormalRadioButton.isEnabled = false
            darkNormalRadioButton.visibility = View.INVISIBLE
            darkNormalLock.visibility = View.VISIBLE
        } else {
            darkNormalRadioButton.isEnabled = true
            darkNormalRadioButton.visibility = View.VISIBLE
            darkNormalLock.visibility = View.INVISIBLE
        }

        if (lightRedLocked) {
            lightRedRadioButton.isEnabled = false
            lightRedRadioButton.visibility = View.INVISIBLE
            lightRedLock.visibility = View.VISIBLE
        } else {
            lightRedRadioButton.isEnabled = true
            lightRedRadioButton.visibility = View.VISIBLE
            lightRedLock.visibility = View.INVISIBLE
        }

        if (darkRedLocked) {
            darkRedRadioButton.isEnabled = false
            darkRedRadioButton.visibility = View.INVISIBLE
            darkRedLock.visibility = View.VISIBLE
        } else {
            darkRedRadioButton.isEnabled = true
            darkRedRadioButton.visibility = View.VISIBLE
            darkRedLock.visibility = View.INVISIBLE
        }
    }

    private fun updateUI() {
        val primaryColor = themeManager.currentThemePrimaryColor

        if (themeManager.currentTheme != Theme.LIGHT_NORMAL) {
            window.statusBarColor = primaryColor
            window.navigationBarColor = primaryColor
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
            window.navigationBarColor = resources.getColor(R.color.black)
        }

        settingsLayout.setBackgroundColor(primaryColor)
        toolbar.setBackgroundColor(primaryColor)
    }
}