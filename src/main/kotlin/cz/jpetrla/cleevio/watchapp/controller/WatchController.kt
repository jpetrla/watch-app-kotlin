package cz.jpetrla.cleevio.watchapp.controller

import cz.jpetrla.cleevio.watchapp.model.Watch
import cz.jpetrla.cleevio.watchapp.service.WatchService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant


@RestController
@RequestMapping("/api/v1")
class WatchController(private val watchService: WatchService) {

	@PostMapping(value = ["/watch"], consumes = [ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
	fun upload(@RequestBody @Valid watch: Watch): ResponseEntity<Any> {
		watchService.upload(watch)
		return createResponseEntity()
	}

	private fun createResponseEntity(): ResponseEntity<Any> {
		val status = HttpStatus.CREATED

        val body = mapOf<String, Any>(
            "timestamp" to Instant.now(),
            "status" to status.value(),
            "message" to "Upload successful"
        )

		return ResponseEntity(body, status)
	}
}