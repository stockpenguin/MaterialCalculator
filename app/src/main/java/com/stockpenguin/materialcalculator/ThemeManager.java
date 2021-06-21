package com.stockpenguin.materialcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;

/*
The ThemeManager class provides the ability to be able to dynamically change the themes

It was initially designed to be a singleton class, however the need to access SharedPreferences of the
CalculatorActivity class is why it is not
 */

public class ThemeManager {
    /*
    Constant variables

    SP_KEY is short for "SharedPreferences Key", simply used to access the SharedPreferences
    to read/write the saved theme

    DEFAULT_THEME is the default theme of the calculator, which is DARK_PURPLE
     */
    private static final String SP_KEY = "CURRENT_THEME";
    public static final Theme DEFAULT_THEME = Theme.DARK_PURPLE;

    /*
    We will use the currentTheme variable to keep track of the currentTheme (duh)

    DarkPurple is the default theme, however calling loadTheme() from the constructor will set it to whatever is saved
    in the SharedPreferences variable

    context is the reference to the passed context, it is necessary in order to access the SharedPreferences

    sp is a global variable of the context's SharedPreferences
     */
    private Theme currentTheme;
    private final Context context;

    /*
    constructor
     */
    public ThemeManager(Context context) {
        this.context = context;
        currentTheme = loadTheme();
    }

    /* called so activities can see if another activity changed the theme */
    public void update() {
        currentTheme = loadTheme();
    }

    /*
    This loadTheme() method reads the SharedPreferences to see if a theme is already saved. It uses the SP_KEY to
    identify one. If a saved Theme does not exist, it sets it to our default DarkPurple theme

    This method returns our Theme enumeration, as it is used to load it into the currentTheme member variable
     */
    private Theme loadTheme() {
        SharedPreferences sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        return Theme.valueOf(sp.getString(SP_KEY, DEFAULT_THEME.toString()));
    }

    /*
    This saveTheme() method takes a Theme enumeration as an input, and writes it to our SharedPreferences using the
    SP_KEY string
     */
    private void saveTheme(Theme theme) {
        SharedPreferences sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_KEY, theme.toString()).apply();
    }

    /*
    setCurrentTheme() will be public. It is intended for our CalculatorActivity to call the ThemeManager and write the
    selected theme to the SharedPreferences, then it will set the instance's currentTheme member variable to the theme
    we just wrote to SharedPreferences.
     */
    public void setCurrentTheme(Theme theme) {
        saveTheme(theme);
        currentTheme = theme;
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    /*
    getCurrentThemePrimary() is another public method intended for the CalculatorActivity class to call. This method
    simply allows us to take the currentTheme enumeration and return the actual PRIMARY color that is saved in our
    colors.xml file.
    -
    Since this app will have support all the way back to Android Lollipop, we have two different ways to return the
    int value of our color. If the version is Build.VERSION_CODES.M, which is Android 6.0 Marshmallow, we will use the
    getColor() method to return it. If the version is under Android 6.0, we will use getResources().getColor(), which
    is deprecated above Android 6.0, but necessary for the past versions.
     */
    public int getCurrentThemePrimaryColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getColor(R.color.lightPurplePrimary);
                case LIGHT_NORMAL:
                    return context.getColor(R.color.lightNormalPrimary);
                case DARK_NORMAL:
                    return context.getColor(R.color.darkNormalPrimary);
                case LIGHT_RED:
                    return context.getColor(R.color.lightRedPrimary);
                case DARK_RED:
                    return context.getColor(R.color.darkRedPrimary);
                default:
                    return context.getColor(R.color.darkPurplePrimary);
            }
        } else {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getResources().getColor(R.color.lightPurplePrimary);
                case LIGHT_NORMAL:
                    return context.getResources().getColor(R.color.lightNormalPrimary);
                case DARK_NORMAL:
                    return context.getResources().getColor(R.color.darkNormalPrimary);
                case LIGHT_RED:
                    return context.getResources().getColor(R.color.lightRedPrimary);
                case DARK_RED:
                    return context.getResources().getColor(R.color.darkRedPrimary);
                default:
                    return context.getResources().getColor(R.color.darkPurplePrimary);
            }
        }
    }

    /*
    same as getCurrentThemePrimary() except for the accent colors of the themes.
     */
    public int getCurrentThemeAccentColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getColor(R.color.lightPurpleAccent);
                case LIGHT_NORMAL:
                    return context.getColor(R.color.lightNormalAccent);
                case DARK_NORMAL:
                    return context.getColor(R.color.darkNormalAccent);
                case LIGHT_RED:
                    return context.getColor(R.color.lightRedAccent);
                case DARK_RED:
                    return context.getColor(R.color.darkRedAccent);
                default:
                    return context.getColor(R.color.darkPurpleAccent);
            }
        } else {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getResources().getColor(R.color.lightPurpleAccent);
                case LIGHT_NORMAL:
                    return context.getResources().getColor(R.color.lightNormalAccent);
                case DARK_NORMAL:
                    return context.getResources().getColor(R.color.darkNormalAccent);
                case LIGHT_RED:
                    return context.getResources().getColor(R.color.lightRedAccent);
                case DARK_RED:
                    return context.getResources().getColor(R.color.darkRedAccent);
                default:
                    return context.getResources().getColor(R.color.darkPurpleAccent);
            }
        }
    }

    public Drawable getCurrentThemeOperatorBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getDrawable(R.drawable.button_calculator_button_white);
                case DARK_PURPLE:
                    return context.getDrawable(R.drawable.button_calculator_button_white);
                case LIGHT_NORMAL:
                    return context.getDrawable(R.drawable.button_calculator_button_black);
                case DARK_NORMAL:
                    return context.getDrawable(R.drawable.button_calculator_button_white);
                case LIGHT_RED:
                    return context.getDrawable(R.drawable.button_calculator_button_white);
                case DARK_RED:
                    return context.getDrawable(R.drawable.button_calculator_button_white);
                default:
                    return context.getDrawable(R.drawable.button_calculator_button);
            }
        } else {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_white);
                case DARK_PURPLE:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_white);
                case LIGHT_NORMAL:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_black);
                case DARK_NORMAL:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_white);
                case LIGHT_RED:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_white);
                case DARK_RED:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_white);
                default:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button);
            }
        }
    }

    public Drawable getCurrentThemeCompileButtonBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getDrawable(R.drawable.button_calculator_darkpurple);
                case DARK_PURPLE:
                    return context.getDrawable(R.drawable.button_calculator_lightpurple);
                case LIGHT_NORMAL:
                    return context.getDrawable(R.drawable.button_calculator_button_black);
                case DARK_NORMAL:
                    return context.getDrawable(R.drawable.button_calculator_button_white);
                case LIGHT_RED:
                    return context.getDrawable(R.drawable.button_calculator_darkred);
                case DARK_RED:
                    return context.getDrawable(R.drawable.button_calculator_lightred);
                default:
                    return context.getDrawable(R.drawable.button_calculator_button);
            }
        } else {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getResources().getDrawable(R.drawable.button_calculator_darkpurple);
                case DARK_PURPLE:
                    return context.getResources().getDrawable(R.drawable.button_calculator_lightpurple);
                case LIGHT_NORMAL:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_black);
                case DARK_NORMAL:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button_white);
                case LIGHT_RED:
                    return context.getResources().getDrawable(R.drawable.button_calculator_darkred);
                case DARK_RED:
                    return context.getResources().getDrawable(R.drawable.button_calculator_lightred);
                default:
                    return context.getResources().getDrawable(R.drawable.button_calculator_button);
            }
        }
    }

    public int getCurrentThemeNumberTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getColor(R.color.white);
                case DARK_PURPLE:
                    return context.getColor(R.color.white);
                case LIGHT_NORMAL:
                    return context.getColor(R.color.black);
                case DARK_NORMAL:
                    return context.getColor(R.color.white);
                case LIGHT_RED:
                    return context.getColor(R.color.white);
                case DARK_RED:
                    return context.getColor(R.color.white);
                default:
                    return -1;
            }
        } else {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return context.getResources().getColor(R.color.white);
                case DARK_PURPLE:
                    return context.getResources().getColor(R.color.white);
                case LIGHT_NORMAL:
                    return context.getResources().getColor(R.color.black);
                case DARK_NORMAL:
                    return context.getResources().getColor(R.color.white);
                case LIGHT_RED:
                    return context.getResources().getColor(R.color.white);
                case DARK_RED:
                    return context.getResources().getColor(R.color.white);
                default:
                    return -1;
            }
        }
    }
}
