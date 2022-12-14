package hu.levente.fazekas.shared.use_case

class ValidateTerms {

    fun execute(acceptedTerms: Boolean): ValidationResult {
        if(!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Fogadd el a felhasználási feltételeket"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}