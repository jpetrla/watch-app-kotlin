package cz.jpetrla.cleevio.watchapp.controller

import com.ninjasquad.springmockk.MockkBean
import cz.jpetrla.cleevio.watchapp.service.WatchService
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.IOException

@WebMvcTest
class WatchControllerTest(
	@param:Autowired private val mockMvc: MockMvc
) {

	@MockkBean
	lateinit var watchService: WatchService

	@Test
	fun testUpload_json_created() {
		every { watchService.upload(any()) } returns Unit

		val content = readRequestContent("WatchControllerTest/upload-created.json")

		mockMvc.perform(post("/api/v1/watch")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content))
			.andDo(print())
			.andExpect(status().isCreated)

		verify(exactly = 1) { watchService.upload(any()) }
	}

	@Test
	fun testUpload_xml_created() {
		every { watchService.upload(any()) } returns Unit

		val content = readRequestContent("WatchControllerTest/upload-created.xml")

		mockMvc.perform(post("/api/v1/watch")
			.contentType(MediaType.APPLICATION_XML)
			.content(content))
			.andDo(print())
			.andExpect(status().isCreated)

		verify(exactly = 1) { watchService.upload(any()) }
	}

	@Throws(IOException::class)
	private fun readRequestContent(path: String): String {
		val resource: Resource = ClassPathResource(path)
        resource.inputStream.use { input ->
            return input.readBytes().toString(Charsets.UTF_8)
        }
	}
}