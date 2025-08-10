package cz.jpetrla.cleevio.watchapp.controller

import cz.jpetrla.cleevio.watchapp.model.Watch
import cz.jpetrla.cleevio.watchapp.repository.WatchJpaRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WatchControllerIT (
    @param:LocalServerPort private val port: Int,
    @param:Autowired private val template: TestRestTemplate,
    @param:Autowired private val watchJpaRepository: WatchJpaRepository
) {

    @AfterEach
    fun tearDown() {
        watchJpaRepository.deleteAll()
    }

	@Test
	fun testUpload() {
		val watch = Watch(
			"dummyTitle",
			2500,
			"dummyDescription",
		"R0lGODlhAQABAIAAAAUEBA==".toByteArray())

		val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
		val request = HttpEntity(watch, headers)
		val url = "http://localhost:$port/api/v1/watch"

		assertEquals(0, watchJpaRepository.findAll().size)

		val responseEntity: ResponseEntity<Any> = template.postForEntity(url, request, Any::class.java)

		assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
		assertEquals(1, watchJpaRepository.count())
	}
}