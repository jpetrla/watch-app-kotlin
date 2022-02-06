package cz.jpetrla.cleevio.watchapp.repository.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "watch")
class WatchEntity (

	@Column(name = "title")
	var title: String,

	@Column(name = "price")
	var price: Int,

	@Column(name = "description")
	var description: String,

	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "fountain")
	var fountain: ByteArray
) {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	val id: UUID = UUID.randomUUID()
}