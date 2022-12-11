package hu.levente.fazekas.shared.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)