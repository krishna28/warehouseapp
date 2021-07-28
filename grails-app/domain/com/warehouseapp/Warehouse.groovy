package com.warehouseapp

import grails.compiler.GrailsCompileStatic
import grails.databinding.BindingFormat

@GrailsCompileStatic
class Warehouse implements Serializable {

    static mapWith = "mongo"

    String id
    String datasource
    String campaign
    @BindingFormat('MM/dd/yy')
    Date daily
    Integer clicks
    Integer impressions

    static constraints = {
        datasource nullable: false
        campaign nullable: false
        daily nullable: false
        clicks nullable: false
        impressions nullable: false
    }
}
