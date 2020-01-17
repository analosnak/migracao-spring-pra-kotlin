package br.com.alura.forum.validator.dto

class ValidationErrorsOutputDto {
    val globalErrorMessages = mutableListOf<String>()
    val errors = mutableListOf<FieldErrorOutputDto>()

    fun getNumberOfErrors() = globalErrorMessages.size + errors.size

    fun addError(message: String) { globalErrorMessages.add(message) }

    fun addFieldError(field: String, message: String) {
        errors.add(FieldErrorOutputDto(field, message))
    }

}