package com.david.weatherchannel.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.repeatOnLifecycleStarted(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycle.coroutineScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

inline fun Fragment.fragmentBooleanResult(
    requestKey: String,
    bundleKey: String,
    crossinline onDenied: () -> Unit = {},
    crossinline onGranted: () -> Unit
) {
    setFragmentResultListener(requestKey) { _, bundle ->
        if (bundle.getBoolean(bundleKey, false)) onGranted() else onDenied()
    }
}