package info.jk.validator.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime
import java.util.*

class ResultError {
    val protocol = UUID.randomUUID()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val createdAt: ZonedDateTime = ZonedDateTime.now()
    val errors = mutableSetOf<Error>()

    class Error(val code: String, val field: String, val message: String) { }
}
