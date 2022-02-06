package cz.jpetrla.cleevio.watchapp.controller

import cz.jpetrla.cleevio.watchapp.model.Watch
import cz.jpetrla.cleevio.watchapp.repository.WatchJpaRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WatchControllerIT (
	@LocalServerPort private val port: Int,
	@Autowired private val template: TestRestTemplate,
	@Autowired private val watchJpaRepository: WatchJpaRepository
) {

	@Test
	fun testUpload() {
		val watch = Watch(
			"dummyTitle",
			2500,
			"dummyDescription",
		"R0lGODlhAQABAIAAAAUEBA==".toByteArray())

		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType.APPLICATION_JSON

		val httpEntity = HttpEntity(watch, httpHeaders)

		val url = "http://localhost:$port/api/v1/watch"

		assertEquals(0, watchJpaRepository.findAll().size)

		val responseEntity: ResponseEntity<Any> = template.exchange(url, HttpMethod.POST, httpEntity, Any::class.java)

		assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
		assertEquals(1, watchJpaRepository.findAll().size)

		watchJpaRepository.deleteAll()
	}
}