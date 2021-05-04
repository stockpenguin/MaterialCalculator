package com.stockpenguin.materialcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;

/*
The ThemeManager class provides the ability to be able to dynamically change the themes.
-
It is a singleton class.
 */

public class ThemeManager extends AppCompatActivity {
    /*
    Due to the fact that this is a singleton, we have a recursive variable for the one and only instance of this class.
    -
    The String SP_KEY is short for "SharedPreferences Key". The ThemeManager uses SharedPreferences to store and load
    the current theme that the user has selected.
    -
    Using a Theme enumeration class, we set the default theme to the DarkPurple theme.
     */
    private static ThemeManager instance;
    private final String SP_KEY = "CURRENT_THEME";
    private final Theme DEFAULT_THEME = Theme.DARK_PURPLE;

    /*
    We will use the currentTheme variable to keep track of the currentTheme.
    -
    We initially set it to DarkPurple, however the output of the loadTheme method will set this in the constructor.
     */
    private Theme currentTheme;

    /*
    Since I want the ThemeManager to be a singleton class, the constructor is private. All it does is load the current
    theme that is saved in the SharedPreferences.
     */
    private ThemeManager() {
        currentTheme = loadTheme();
    }

    /*
    getInstance() method to acquire an instance of this singleton. If the instance is null, we simply create one.
     */
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    /*
    This loadTheme() method reads the SharedPreferences to see if a theme is already saved. It uses the SP_KEY to
    identify one. If a saved Theme does not exist, it sets it to our default DarkPurple theme.
    -
    This method returns our Theme enumeration, as it is used to load it into the currentTheme member variable.
     */
    private Theme loadTheme() {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        return Theme.valueOf(sp.getString(SP_KEY, DEFAULT_THEME.toString()));
    }

    /*
    This saveTheme() method takes a Theme enumeration as an input, and writes it to our SharedPreferences using the
    SP_KEY string.
     */
    private void saveTheme(Theme theme) {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(SP_KEY, theme.toString()).apply();
    }

    /*
    setCurrentTheme() will be public. It is intended for our CalculatorActivity to call the ThemeManager and write the
    selected theme to the SharedPreferences, then it will set the instance's currentTheme member varaible to the theme
    we just wrote to SharedPreferences.
     */
    public void setCurrentTheme(Theme theme) {
        saveTheme(theme);
        currentTheme = theme;
    }

    /*
    getCurrentThemePrimary() is another public method intended for the CalculatorActivity class to call. This method
    simply allows us to take the currentTheme enumeration and return the actual PRIMARY color that is saved in our
    colors.xml file.
    -
    Since this app will have support all the way back to Android Jellybean, we have two different ways to return the
    int value of our color. If the version is Build.VERSION_CODES.M, which is Android 6.0 Marshmallow, we will use the
    getColor() method to return it. If the version is under Android 6.0, we will use getResources().getColor(), which
    is deprecated above Android 6.0, but necessary for the past versions.
     */
    public int getCurrentThemePrimary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return getColor(R.color.lightPurplePrimary);
                case LIGHT_NORMAL:
                    return getColor(R.color.lightNormalPrimary);
                case DARK_NORMAL:
                    return getColor(R.color.darkNormalPrimary);
                case LIGHT_RED:
                    return getColor(R.color.lightRedPrimary);
                case DARK_RED:
                    return getColor(R.color.darkRedPrimary);
                default:
                    return getColor(R.color.darkPurplePrimary);
            }
        } else {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return getResources().getColor(R.color.lightPurplePrimary);
                case LIGHT_NORMAL:
                    return getResources().getColor(R.color.lightNormalPrimary);
                case DARK_NORMAL:
                    return getResources().getColor(R.color.darkNormalPrimary);
                case LIGHT_RED:
                    return getResources().getColor(R.color.lightRedPrimary);
                case DARK_RED:
                    return getResources().getColor(R.color.darkRedPrimary);
                default:
                    return getResources().getColor(R.color.darkPurplePrimary);
            }
        }
    }

    /*
    same as getCurrentThemePrimary() except for the accent colors of the themes.
     */
    public int getCurrentThemeAccent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return getColor(R.color.lightPurpleAccent);
                case LIGHT_NORMAL:
                    return getColor(R.color.lightNormalAccent);
                case DARK_NORMAL:
                    return getColor(R.color.darkNormalAccent);
                case LIGHT_RED:
                    return getColor(R.color.lightRedAccent);
                case DARK_RED:
                    return getColor(R.color.darkRedAccent);
                default:
                    return getColor(R.color.darkPurpleAccent);
            }
        } else {
            switch (currentTheme) {
                case LIGHT_PURPLE:
                    return getResources().getColor(R.color.lightPurpleAccent);
                case LIGHT_NORMAL:
                    return getResources().getColor(R.color.lightNormalAccent);
                case DARK_NORMAL:
                    return getResources().getColor(R.color.darkNormalAccent);
                case LIGHT_RED:
                    return getResources().getColor(R.color.lightRedAccent);
                case DARK_RED:
                    return getResources().getColor(R.color.darkRedAccent);
                default:
                    return getResources().getColor(R.color.darkPurpleAccent);
            }
        }
    }
}
