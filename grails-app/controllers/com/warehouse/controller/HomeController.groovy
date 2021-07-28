package com.warehouse.controller


import grails.converters.JSON

class HomeController {
	static responseFormats = ['json', 'xml']

    def warehouseService
    def mongoNativeWarehouseService
	
    def getAllByDatasourceAndDateRange() {
        if(!params.datasource || !params.to || !params.from){
            respond([message:'Bad request,please datasource, to and from in the query params'], status: 422)
            return
        }
        def result = warehouseService.getAllByDatasourceAndDateRange(params)
        render "Total clicks: ${result[0]}"
    }

    def getImpressionsOverTime() {
        if(!params.date){
            respond([message:'Bad request,please pass date as query params'], status: 422)
            return
        }
        def result = warehouseService.getImpressionsOverTime(params)
        render "Total impressions: ${result[0]}"
    }

    def getCtrPerDataSourceAndCampaign() {
        if(!params.groupByFields){
            respond([message:'Bad request,please pass groupByFields as query params'], status: 422)
            return
        }
        def result = mongoNativeWarehouseService.getCtrPerDataSourceAndCampaign(params)
        render result as JSON
    }
}
