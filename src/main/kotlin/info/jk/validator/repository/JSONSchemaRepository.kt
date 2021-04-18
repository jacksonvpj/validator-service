package info.jk.validator.repository

import info.jk.validator.entity.JSONSchema
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class JSONSchemaRepository: PanacheRepository<JSONSchema> {

    fun findByCode(name: String) = find("code", name).firstResult()

}