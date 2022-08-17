package com.cute.connection.ui.main.screens.listing

interface AdapterClickListener {
    fun triggerCopyUrlClickEvent(position: Int)
    fun triggerDeleteUrlClickEvent(position: Int)
}

