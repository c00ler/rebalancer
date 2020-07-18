package com.bcgdv.rebalancer

import org.apache.commons.csv.CSVFormat

object ApplicationDefaults {

    val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader()
}
