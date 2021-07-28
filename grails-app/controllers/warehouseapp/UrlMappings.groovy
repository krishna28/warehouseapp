package warehouseapp

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        group "/api", {
            "/allByDatasourceAndDateRange"(controller: 'home', action:'getAllByDatasourceAndDateRange')
            "/impressionsOverTime"(controller: 'home', action:'getImpressionsOverTime')
            "/ctrPerDataSourceAndCampaign"(controller: 'home', action:'getCtrPerDataSourceAndCampaign')
        }

        "/"(controller: 'application', action:'welcome')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
