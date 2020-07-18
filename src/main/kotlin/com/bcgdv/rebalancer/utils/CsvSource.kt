package com.bcgdv.rebalancer.utils

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.core.io.Resource
import java.nio.charset.StandardCharsets

interface CsvSource {

    fun <T> parseCsv(resource: Resource, format: CSVFormat, mapper: (CSVRecord) -> T): Collection<T> {
        return CSVParser.parse(resource.inputStream, StandardCharsets.UTF_8, format)
            .use { parser ->
                parser.records.map { record -> mapper(record) }
            }
    }
}
