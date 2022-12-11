package hu.levente.fazekas.shared.use_case

import android.util.Patterns

actual fun matchEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}