package cz.jpetrla.cleevio.watchapp.repository.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@Table(name = "watch")
class WatchEntity (

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID? = null,

	@Column(name = "title", nullable = false)
	var title: String,

	@Column(name = "price", nullable = false)
	var price: Int,

	@Column(name = "description", nullable = false)
	var description: String,

	@Lob
    @JdbcTypeCode(SqlTypes.VARBINARY)
	@Column(name = "fountain", nullable = false, columnDefinition = "VARBINARY")
	var fountain: ByteArray
)