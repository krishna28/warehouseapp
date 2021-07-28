package com.warehouse.controller

import com.warehouse.service.MongoNativeWarehouseService
import com.warehouse.service.WarehouseService
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class HomeControllerSpec extends Specification implements ControllerUnitTest<HomeController> {

    def setup() {
    }

    def cleanup() {

    }

    void "getAllByDatasourceAndDateRange call should fail if called with no params"() {
        when:
        controller.getAllByDatasourceAndDateRange()

        then:
        response.status == 422
    }
    void "getAllByDatasourceAndDateRange call should pass if called with params"() {
        given:
        controller.warehouseService = Mock(WarehouseService) {
           1 * getAllByDatasourceAndDateRange(_) >> [9900]
        }
        when:
        params.datasource = 'google ads'
        params.to = '11/13/19'
        params.from = '11/14/19'
        controller.getAllByDatasourceAndDateRange()

        then:
        response.status == 200
        response.text == 'Total click: 9900'
    }
    void "getImpressionsOverTime call should fail if called with no params"() {
        when:
        controller.getImpressionsOverTime()

        then:
        response.status == 422
    }
    void "getImpressionsOverTime call should pass if called with date as params"() {
        given:
        controller.warehouseService = Mock(WarehouseService) {
            1 * getImpressionsOverTime(_) >> [7654]
        }
        when:
        params.date = '11/13/19'
        controller.getImpressionsOverTime()

        then:
        response.status == 200
        response.text == 'Total click: 7654'
    }
    void "getCtrPerDataSourceAndCampaign call should fail if called with no params"() {
        when:
        controller.getCtrPerDataSourceAndCampaign()

        then:
        response.status == 422
    }
    void "getCtrPerDataSourceAndCampaign call should pass if called with groupByFields params"() {
        given:
        controller.mongoNativeWarehouseService = Mock(MongoNativeWarehouseService) {
            1 * getCtrPerDataSourceAndCampaign(_) >> [message:"Success"]
        }
        when:
        params.groupByFields = "datasource,campaign"
        controller.getCtrPerDataSourceAndCampaign()

        then:
        response.status == 200
    }

}