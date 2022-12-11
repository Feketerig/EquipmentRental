package hu.levente.fazekas.shared.use_case

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "A két jelszó nem egyezik meg"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}