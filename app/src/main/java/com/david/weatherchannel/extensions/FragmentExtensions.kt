package com.david.weatherchannel.extensions

import androidx.fragment.app.Fragment
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