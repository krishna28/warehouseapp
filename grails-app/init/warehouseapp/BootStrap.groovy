package warehouseapp

import com.warehouseapp.Warehouse

class BootStrap {

    def init = { servletContext ->

        if (!Warehouse.count()) {
            def headers = []
            def myData = this.class.getResourceAsStream('/data/analytics.csv')
            myData.readLines("UTF-8").eachWithIndex { entry, i ->
                if (i == 0) {
                    headers = entry.split(",").collect { it[0].toLowerCase().concat(it[1..-1]) }
                } else {
                    def map = [:]
                    entry.split(",").eachWithIndex { String item, int j ->
                        map[headers[j]] = item
                    }
                    new Warehouse(map).save(failOnError: true)
                }
            }
        }

    }
    def destroy = {
    }
}
