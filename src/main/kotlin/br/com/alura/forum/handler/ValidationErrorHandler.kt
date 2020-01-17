package br.com.alura.forum.handler

import br.com.alura.forum.validator.dto.ValidationErrorsOutputDto
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidationErrorHandler(private val messageSource: MessageSource) {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(exception: MethodArgumentNotValidException): ValidationErrorsOutputDto {
        val globalErrors = exception.bindingResult.globalErrors
        val fieldErrors = exception.bindingResult.fieldErrors

        val validationErrors = ValidationErrorsOutputDto()

        globalErrors.forEach { validationErrors.addError(getErrorMessage(it)) }

        fieldErrors.forEach {
            validationErrors.addFieldError(it.field, getErrorMessage(it))
        }

        return validationErrors
    }

    private fun getErrorMessage(error: ObjectError) =
            messageSource.getMessage(error, LocaleContextHolder.getLocale())
}