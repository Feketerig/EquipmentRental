package hu.levente.fazekas.shared.use_case

import hu.levente.fazekas.shared.utils.validators.isValidEmail

actual fun matchEmail(email: String): Boolean {
    return email.isValidEmail()
}