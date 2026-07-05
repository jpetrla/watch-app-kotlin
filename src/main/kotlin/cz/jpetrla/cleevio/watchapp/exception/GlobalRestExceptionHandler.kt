package cz.jpetrla.cleevio.watchapp.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import tools.jackson.core.JacksonException
import tools.jackson.databind.exc.InvalidFormatException
import java.util.Date

@RestControllerAdvice
class GlobalRestExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = listOf(ex.message.orEmpty())

        return createResponseEntity(errors, headers, statusCode)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }

        return createResponseEntity(errors, headers, statusCode)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val cause = ex.rootCause
        val message = if (cause is InvalidFormatException) {
            val path: List<JacksonException.Reference> = cause.path
            val fieldName = path.first().propertyName
            "$fieldName: ${cause.originalMessage}"
        } else {
            ex.message.orEmpty()
        }

        val errors = listOf(message)

        return createResponseEntity(errors, headers, statusCode)
    }

    private fun createResponseEntity(errors: List<String>, headers: HttpHeaders, statusCode: HttpStatusCode): ResponseEntity<Any> {
        val body = linkedMapOf<String, Any>(
            "timestamp" to Date(),
            "statusCode" to statusCode.value(),
            "errors" to errors
        )

        return ResponseEntity(body, headers, statusCode)
    }
}
