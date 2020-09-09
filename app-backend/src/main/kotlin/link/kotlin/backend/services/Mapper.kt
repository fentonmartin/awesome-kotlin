package link.kotlin.backend.services

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Create new instance of [XmlMapper]
 *
 * @author Ruslan Ibragimov
 * @since 2020.03
 */
fun createXmlMapper(): XmlMapper {
    return XmlMapper().also { it.registerKotlinModule() }
}
