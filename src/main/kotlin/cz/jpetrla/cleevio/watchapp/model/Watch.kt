package cz.jpetrla.cleevio.watchapp.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class Watch (

	@field:NotBlank
	val title: String,

	@field:NotNull
	@field:Positive
    val price: Int,

	@field:NotBlank
    val description: String,

	@field:NotEmpty
    val fountain: ByteArray
) {

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Watch

		if (title != other.title) return false
		if (price != other.price) return false
		if (description != other.description) return false
		if (!fountain.contentEquals(other.fountain)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = title.hashCode()
		result = 31 * result + price
		result = 31 * result + description.hashCode()
		result = 31 * result + fountain.contentHashCode()
		return result
	}

	override fun toString(): String {
		return "Watch(title='$title', price=$price, description='$description')"
	}
}