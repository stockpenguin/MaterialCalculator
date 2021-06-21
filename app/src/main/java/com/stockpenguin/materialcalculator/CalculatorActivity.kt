package com.stockpenguin.materialcalculator

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.MobileAds
import java.lang.Exception

class CalculatorActivity : AppCompatActivity() {
    private lateinit var themeManager: ThemeManager

    private lateinit var calculatorLayout: ConstraintLayout
    private lateinit var calculatorTextView: TextView

    private lateinit var clearButton: Button
    private lateinit var negateButton: Button
    private lateinit var percentButton: Button
    private lateinit var decimalButton: Button

    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var divideButton: Button

    private lateinit var zeroButton: Button
    private lateinit var oneButton: Button
    private lateinit var twoButton: Button
    private lateinit var threeButton: Button
    private lateinit var fourButton: Button
    private lateinit var fiveButton: Button
    private lateinit var sixButton: Button
    private lateinit var sevenButton: Button
    private lateinit var eightButton: Button
    private lateinit var nineButton: Button

    private lateinit var settingsButton: Button

    private lateinit var compileButton: Button

    private var currentEquation = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        init()
    }

    override fun onResume() {
        super.onResume()
        themeManager.update()
        updateUI()
    }

    private fun init() {
        MobileAds.initialize(this) {}

        calculatorLayout = findViewById(R.id.calculatorLayout_CalculatorActivity)
        calculatorTextView = findViewById(R.id.calculatorTextView_CalculatorActivity)

        clearButton = findViewById(R.id.buttonClear_CalculatorActivity)
        negateButton = findViewById(R.id.buttonNegate_CalculatorActivity)
        percentButton = findViewById(R.id.buttonPercent_CalculatorActivity)
        decimalButton = findViewById(R.id.buttonDecimal_CalculatorActivity)
        addButton = findViewById(R.id.buttonAdd_CalculatorActivity)
        subtractButton = findViewById(R.id.buttonSubtract_CalculatorActivity)
        multiplyButton = findViewById(R.id.buttonMultiply_CalculatorActivity)
        divideButton = findViewById(R.id.buttonDivide_CalculatorActivity)
        zeroButton = findViewById(R.id.buttonZero_CalculatorActivity)
        oneButton = findViewById(R.id.buttonOne_CalculatorActivity)
        twoButton = findViewById(R.id.buttonTwo_CalculatorActivity)
        threeButton = findViewById(R.id.buttonThree_CalculatorActivity)
        fourButton = findViewById(R.id.buttonFour_CalculatorActivity)
        fiveButton = findViewById(R.id.buttonFive_CalculatorActivity)
        sixButton = findViewById(R.id.buttonSix_CalculatorActivity)
        sevenButton = findViewById(R.id.buttonSeven_CalculatorActivity)
        eightButton = findViewById(R.id.buttonEight_CalculatorActivity)
        nineButton = findViewById(R.id.buttonNine_CalculatorActivity)
        settingsButton = findViewById(R.id.buttonSettings_CalculatorActivity)
        compileButton = findViewById(R.id.buttonEqual_CalculatorActivity)

        clearButton.setOnClickListener { processButton(ButtonType.CLEAR_BUTTON) }
        negateButton.setOnClickListener { processButton(ButtonType.NEGATE_BUTTON) }
        percentButton.setOnClickListener { processButton(ButtonType.PERCENT_BUTTON) }
        decimalButton.setOnClickListener { processButton(ButtonType.DECIMAL_BUTTON) }
        addButton.setOnClickListener { processButton(ButtonType.ADD_BUTTON) }
        subtractButton.setOnClickListener { processButton(ButtonType.SUBTRACT_BUTTON) }
        multiplyButton.setOnClickListener { processButton(ButtonType.MULTIPLY_BUTTON) }
        divideButton.setOnClickListener { processButton(ButtonType.DIVIDE_BUTTON) }
        zeroButton.setOnClickListener { processButton(ButtonType.ZERO_BUTTON) }
        oneButton.setOnClickListener { processButton(ButtonType.ONE_BUTTON) }
        twoButton.setOnClickListener { processButton(ButtonType.TWO_BUTTON) }
        threeButton.setOnClickListener { processButton(ButtonType.THREE_BUTTON) }
        fourButton.setOnClickListener { processButton(ButtonType.FOUR_BUTTON) }
        fiveButton.setOnClickListener { processButton(ButtonType.FIVE_BUTTON) }
        sixButton.setOnClickListener { processButton(ButtonType.SIX_BUTTON) }
        sevenButton.setOnClickListener { processButton(ButtonType.SEVEN_BUTTON) }
        eightButton.setOnClickListener { processButton(ButtonType.EIGHT_BUTTON) }
        nineButton.setOnClickListener { processButton(ButtonType.NINE_BUTTON) }
        settingsButton.setOnClickListener { processButton(ButtonType.SETTINGS_BUTTON) }
        compileButton.setOnClickListener { processButton(ButtonType.COMPILE_BUTTON) }

        themeManager = ThemeManager(this)

        // don't ask
        negateButton.isEnabled = false
        negateButton.visibility = View.INVISIBLE
        percentButton.isEnabled = false
        percentButton.visibility = View.INVISIBLE

        updateUI()
    }

    private fun processButton(type: ButtonType) {
        updateUI()

        if (currentEquation == "0" || currentEquation == "0.0" || currentEquation == SyntaxError.MSG) {
            currentEquation = ""
        }

        when (type) {
            ButtonType.CLEAR_BUTTON -> currentEquation = "0"
            ButtonType.NEGATE_BUTTON -> currentEquation += '-'
            ButtonType.PERCENT_BUTTON -> currentEquation += '%'
            ButtonType.DECIMAL_BUTTON -> currentEquation += '.'
            ButtonType.ADD_BUTTON -> currentEquation += '+'
            ButtonType.SUBTRACT_BUTTON -> currentEquation += '-'
            ButtonType.MULTIPLY_BUTTON -> currentEquation += '*'
            ButtonType.DIVIDE_BUTTON -> currentEquation += '/'
            ButtonType.ZERO_BUTTON -> currentEquation += '0'
            ButtonType.ONE_BUTTON -> currentEquation += '1'
            ButtonType.TWO_BUTTON -> currentEquation += '2'
            ButtonType.THREE_BUTTON -> currentEquation += '3'
            ButtonType.FOUR_BUTTON -> currentEquation += '4'
            ButtonType.FIVE_BUTTON -> currentEquation += '5'
            ButtonType.SIX_BUTTON -> currentEquation += '6'
            ButtonType.SEVEN_BUTTON -> currentEquation += '7'
            ButtonType.EIGHT_BUTTON -> currentEquation += '8'
            ButtonType.NINE_BUTTON -> currentEquation += '9'
            ButtonType.COMPILE_BUTTON -> compile()
            ButtonType.SETTINGS_BUTTON -> {
                if (currentEquation == "") currentEquation += '0'
                openSettings()
            }
        }

        updateUI()
    }

    private fun compile() {
        currentEquation = try {
            Compiler.getInstance().solve(currentEquation)
        } catch (e: Exception) {
            SyntaxError.MSG
        }
        updateUI()
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI() {
        val primaryColor = themeManager.currentThemePrimaryColor
        val accentColor = themeManager.currentThemeAccentColor
        val operatorBg = themeManager.currentThemeOperatorBackgroundColor
        val compileBg = themeManager.currentThemeCompileButtonBackgroundColor
        val numTextColor = themeManager.currentThemeNumberTextColor

        calculatorTextView.text = currentEquation

        if (themeManager.currentTheme != Theme.LIGHT_NORMAL) {
            window.statusBarColor = primaryColor
            window.navigationBarColor = primaryColor
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
            window.navigationBarColor = resources.getColor(R.color.black)
        }

        calculatorLayout.setBackgroundColor(primaryColor)

        divideButton.background = operatorBg
        multiplyButton.background = operatorBg
        subtractButton.background = operatorBg
        addButton.background = operatorBg

        divideButton.setTextColor(primaryColor)
        multiplyButton.setTextColor(primaryColor)
        subtractButton.setTextColor(primaryColor)
        addButton.setTextColor(primaryColor)

        clearButton.setTextColor(accentColor)

        compileButton.background = compileBg

        calculatorTextView.setTextColor(numTextColor)

        compileButton.setTextColor(primaryColor)

        decimalButton.setTextColor(numTextColor)
        zeroButton.setTextColor(numTextColor)
        oneButton.setTextColor(numTextColor)
        twoButton.setTextColor(numTextColor)
        threeButton.setTextColor(numTextColor)
        fourButton.setTextColor(numTextColor)
        fiveButton.setTextColor(numTextColor)
        sixButton.setTextColor(numTextColor)
        sevenButton.setTextColor(numTextColor)
        eightButton.setTextColor(numTextColor)
        nineButton.setTextColor(numTextColor)
        percentButton.setTextColor(numTextColor)
        negateButton.setTextColor(numTextColor)
    }
}