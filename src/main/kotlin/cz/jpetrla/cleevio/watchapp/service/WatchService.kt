package cz.jpetrla.cleevio.watchapp.service

import cz.jpetrla.cleevio.watchapp.model.Watch
import cz.jpetrla.cleevio.watchapp.repository.WatchJpaRepository
import cz.jpetrla.cleevio.watchapp.repository.entity.WatchEntity
import org.springframework.stereotype.Service

@Service
class WatchService(private val watchJpaRepository: WatchJpaRepository) {

	fun upload(watch: Watch) {
		val watchEntity = WatchEntity(
			title = watch.title,
			price = watch.price,
			description = watch.description,
			fountain = watch.fountain
		)
		watchJpaRepository.save(watchEntity)
	}
}