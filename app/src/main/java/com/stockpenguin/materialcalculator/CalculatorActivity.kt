package com.stockpenguin.materialcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CalculatorActivity : AppCompatActivity() {
    /* ThemeManager */
    private lateinit var themeManager: ThemeManager

    /* Layout and TextView */
    private lateinit var calculatorLayout: ConstraintLayout
    private lateinit var calculatorTextView: TextView

    /* Special Operators */
    private lateinit var clearButton: Button
    private lateinit var negateButton: Button
    private lateinit var percentButton: Button
    private lateinit var decimalButton: Button

    /* Four Function Operators */
    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var divideButton: Button

    /* Numbers */
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

    /* Settings Button */
    private lateinit var settingsButton: Button

    /* Compile Button */
    private lateinit var compileButton: Button

    /* currentEquation on the screen */
    /* set to 0 by default */
    private var currentEquation = "0"

    /* NOTE:
    Throughout the code, the equal sign button is referred to as the "compile" button. This is because rather than
    program each button to do its own processes as in a previous implementation of this app which led to confusing
    spaghetti code, I was inspired by the way compilers work, as I was learning them while making this app.
    Each button simply appends it's string equivalent to the currentEquation text. When the equal sign is pressed,
    the text will then be "compiled" to postfix notation, and essentially a small equivalent of three address
    code. From there, we can execute one step at a time using PEMDAS in order.
     */

    /*
    The platform-specific onCreate function is called when the activity is created (as the name implies)
    we will simply call init() which will initialize everything, which is a lot of initializing
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        init()
    }

    private fun init() {
        /* Initialize Layout and TextView */
        calculatorLayout = findViewById(R.id.calculatorLayout_CalculatorActivity)
        calculatorTextView = findViewById(R.id.calculatorTextView_CalculatorActivity)

        /* Initialize Special Operators */
        clearButton = findViewById(R.id.buttonClear_CalculatorActivity)
        negateButton = findViewById(R.id.buttonNegate_CalculatorActivity)
        percentButton = findViewById(R.id.buttonPercent_CalculatorActivity)
        decimalButton = findViewById(R.id.buttonDecimal_CalculatorActivity)

        /* Initialize Four Function Operators */
        addButton = findViewById(R.id.buttonAdd_CalculatorActivity)
        subtractButton = findViewById(R.id.buttonSubtract_CalculatorActivity)
        multiplyButton = findViewById(R.id.buttonMultiply_CalculatorActivity)
        divideButton = findViewById(R.id.buttonDivide_CalculatorActivity)

        /* Initialize Number Buttons */
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

        /* Initialize Settings Button */
        settingsButton = findViewById(R.id.buttonSettings_CalculatorActivity)

        /* Initialize Compile Button */
        compileButton = findViewById(R.id.buttonEqual_CalculatorActivity)

        /* OnClick for Special Operators */
        clearButton.setOnClickListener { processButton(ButtonType.CLEAR_BUTTON) }
        negateButton.setOnClickListener { processButton(ButtonType.NEGATE_BUTTON) }
        percentButton.setOnClickListener { processButton(ButtonType.PERCENT_BUTTON) }
        decimalButton.setOnClickListener { processButton(ButtonType.DECIMAL_BUTTON) }

        /* OnClick for Four Function Operators */
        addButton.setOnClickListener { processButton(ButtonType.ADD_BUTTON) }
        subtractButton.setOnClickListener { processButton(ButtonType.SUBTRACT_BUTTON) }
        multiplyButton.setOnClickListener { processButton(ButtonType.MULTIPLY_BUTTON) }
        divideButton.setOnClickListener { processButton(ButtonType.DIVIDE_BUTTON) }

        /* OnClick for Number buttons */
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

        /* OnClick for Settings Button */
        settingsButton.setOnClickListener { processButton(ButtonType.SETTINGS_BUTTON) }

        /* OnClick for Compile Button */
        compileButton.setOnClickListener { processButton(ButtonType.COMPILE_BUTTON) }

        themeManager = ThemeManager(this)

        updateUI()
    }

    private fun processButton(type: ButtonType) {
        /* if currentEquation is the default String value, just set it to an empty string so the first char will be
        whatever button is pressed
         */
        if (currentEquation == "0") {
            currentEquation = ""
        }

        /*
        when statement is the kotlin equivalent of a switch statement

        since we will leave all of the processing to the compiler, all we do is append/set the string value of the
        currentEquation string, and call updateUI() when finished

        we simply append raw strings without checking if it actually doesn't belong in the equation.
        Ex. if a user puts "4%20++4.6", there is no runtime syntax checking, so the "compiler" will simply tell the
        user that there is a SyntaxError, because obviously that equation makes no sense
         */
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
            ButtonType.SETTINGS_BUTTON -> openSettings()
        }
        updateUI()
    }

    private fun compile() {
        currentEquation = Compiler.getInstance().infixToPostfix(currentEquation)
        updateUI()
    }

    private fun openSettings() {}

    private fun updateUI() {
        calculatorTextView.text = currentEquation
        calculatorLayout.setBackgroundColor(themeManager.currentThemePrimary)
    }
}