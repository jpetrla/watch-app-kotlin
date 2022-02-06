package cz.jpetrla.cleevio.watchapp.repository

import cz.jpetrla.cleevio.watchapp.repository.entity.WatchEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WatchJpaRepository : JpaRepository<WatchEntity, UUID>