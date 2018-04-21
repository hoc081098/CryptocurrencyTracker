package com.hoc.cryptocurrencytracker.util

import android.content.Context
import android.widget.Toast

fun Context.toast(charSequence: CharSequence) {
    Toast.makeText(this, charSequence, Toast.LENGTH_SHORT).show()
}
