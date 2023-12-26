package com.david.weatherchannel.extensions

import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextSubmit(crossinline listener: (query: String) -> Unit = { _ -> }) =
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            listener(query.orEmpty())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })