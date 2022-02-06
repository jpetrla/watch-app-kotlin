package cz.jpetrla.cleevio.watchapp.service

import cz.jpetrla.cleevio.watchapp.model.Watch
import cz.jpetrla.cleevio.watchapp.repository.WatchJpaRepository
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

	@Test
	fun testUpload() {
		val watch = Watch(
			"dummyTitle",
			2500,
			"dummyDescription",
		"dummyFountain".toByteArray())

		var watchEntities = watchJpaRepository.findAll()
		assertEquals(0, watchEntities.size)

		watchService.upload(watch)

		watchEntities = watchJpaRepository.findAll()
		assertEquals(1, watchEntities.size)

		val watchEntity = watchEntities.stream().findFirst().get()
		assertNotNull(watchEntity.id)
		assertEquals(watch.title, watchEntity.title)
		assertEquals(watch.price, watchEntity.price)
		assertEquals(watch.description, watchEntity.description)
		assertEquals(watch.fountain.size, watchEntity.fountain.size)

		watchJpaRepository.deleteAll()
	}
}