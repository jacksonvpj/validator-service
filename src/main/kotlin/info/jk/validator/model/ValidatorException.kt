package info.jk.validator.model

import com.networknt.schema.ValidationMessage

class ValidatorException(val constraints: Set<ValidationMessage>) : Exception() {

}
