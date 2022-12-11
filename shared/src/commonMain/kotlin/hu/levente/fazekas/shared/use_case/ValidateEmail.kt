package hu.levente.fazekas.shared.use_case

expect fun matchEmail(email: String): Boolean

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Az email mező nem lehet üres"
            )
        }
        if(!matchEmail(email)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Nem érvényes email cím"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}