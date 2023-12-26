package com.david.weatherchannel.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.WindowInsetsCompat

fun View.showSoftInput() {
    context.getSystemService(Context.INPUT_METHOD_SERVICE).let { it as InputMethodManager }
        .showSoftInput(this, 0)
}

fun View.hideSoftInput() {
    context.getSystemService(Context.INPUT_METHOD_SERVICE).let { it as InputMethodManager }
        .hideSoftInputFromWindow(applicationWindowToken, 0)
}

val View.keyboardIsVisible: Boolean
    get() = WindowInsetsCompat
        .toWindowInsetsCompat(rootWindowInsets)
        .isVisible(WindowInsetsCompat.Type.ime())

var View.isKeyboardVisible: Boolean
    get() = keyboardIsVisible
    set(value) {
        if (value) {
            showSoftInput()
        } else {
            hideSoftInput()
        }
    }