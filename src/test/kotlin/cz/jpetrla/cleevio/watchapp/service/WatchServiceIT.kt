package cz.jpetrla.cleevio.watchapp.service

import cz.jpetrla.cleevio.watchapp.model.Watch
import cz.jpetrla.cleevio.watchapp.repository.WatchJpaRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
@ActiveProfiles("test")
class WatchServiceIT @Autowired constructor(
	private val watchJpaRepository: WatchJpaRepository,
	private val watchService: WatchService
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
		"dummyFountain".toByteArray())

		assertEquals(0, watchJpaRepository.count())

		watchService.upload(watch)

		assertEquals(1, watchJpaRepository.count())

		val watchEntity = watchJpaRepository.findAll().single()
		assertNotNull(watchEntity.id)
		assertEquals(watch.title, watchEntity.title)
		assertEquals(watch.price, watchEntity.price)
		assertEquals(watch.description, watchEntity.description)
		assertEquals(watch.fountain.size, watchEntity.fountain.size)
	}
}