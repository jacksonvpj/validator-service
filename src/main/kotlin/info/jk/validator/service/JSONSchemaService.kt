package info.jk.validator.service

import info.jk.validator.entity.JSONSchema
import info.jk.validator.repository.JSONSchemaRepository
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class JSONSchemaService(val jsonSchemaRepository: JSONSchemaRepository) {

    @Transactional
    fun findByCode(code: String): JSONSchema? {
        return jsonSchemaRepository.findByCode(code)
    }
}