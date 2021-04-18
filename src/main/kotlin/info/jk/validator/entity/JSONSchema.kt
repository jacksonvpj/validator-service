package info.jk.validator.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity()
@Table(name = "json_schema")
class JSONSchema: PanacheEntity() {

    lateinit var code: String
    lateinit var schema: String

}