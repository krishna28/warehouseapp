package com.warehouse.service

import com.warehouseapp.Warehouse
import grails.gorm.transactions.Transactional


@Transactional
class WarehouseService {

    /** This method is used for getting the total clicks per datasource with a given date range
     *  @params args
     *  @params args.datasource this is valid datasource input applied as a filter
     *  @params args.from this is valid date input in format (MM/dd/yy)
     *  @params args.to this is valid date input in format (MM/dd/yy)
     *  @return int This is totalClicks over a given datasource and date range
     *
     */
    def getAllByDatasourceAndDateRange(Map args) {
        String dataSource = args.datasource ? args.datasource.toLowerCase() : ""
        String from = args.from?:""
        String to = args.to?:""
        def criteria = Warehouse.createCriteria()
        def results = criteria.list {
            and {
                ilike('datasource', dataSource)
                between('daily', new Date(from), new Date(to))
            }
            projections {
                sum("clicks")
            }
        }
        results
    }

    /** This method is used for getting the total clicks per datasource with a given date
     *  @params args
     *  @params args.date This is a valid date input in format (MM/dd/yy)
     *  @return int This is totalImpression over a given date
     */

    def getImpressionsOverTime(Map args) {
        def criteria = Warehouse.createCriteria()
        def date = new Date(args.date)
        def results = criteria.list {
            and {
                eq('daily', date)
            }
            projections {
                sum("impressions")
            }
        }
        results
    }

}
