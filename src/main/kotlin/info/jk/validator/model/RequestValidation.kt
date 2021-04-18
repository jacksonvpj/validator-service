package info.jk.validator.model

import javax.validation.constraints.NotBlank

class RequestValidation {
    @NotBlank
    val code: String? = null
    @NotBlank
    val jsonBody: String? = null
}
