package com.warehouse.service

import com.mongodb.BasicDBObject
import com.warehouseapp.Warehouse
import grails.gorm.transactions.Transactional
import org.bson.conversions.Bson

import static com.mongodb.client.model.Aggregates.project
import static com.mongodb.client.model.Projections.*

@Transactional
class MongoNativeWarehouseService {

    /** This method is native mongodb query used for getting the click-through-Rate (clicks/impression)*100
     *  @params args
     *  @params args.groupByFields this is used to group the result set, i.e. groupByFields=datasource,campaign
     *  @return LinkedHashMap This represents grouped result which return metrics like CTR,totalImpressions,clicks and campaign
     *
     */
    def getCtrPerDataSourceAndCampaign(Map args) {
        def objIds = [:]
        args.groupByFields.split(',').each {
            objIds.put(it, "\$$it")
        }
        BasicDBObject groupStages = new BasicDBObject('_id', objIds)
                .append('totalClicks', new BasicDBObject('$sum', '$clicks'))
                .append('totalImpressions', new BasicDBObject('$sum', '$impressions'))

        BasicDBObject grp = new BasicDBObject('$group', groupStages)

        Bson clickThroughRate = project(fields(include('totalClicks', 'totalImpressions'),
                computed("CTR", new BasicDBObject('$multiply',
                        [new BasicDBObject('$divide', ['$totalClicks', '$totalImpressions']),
                         100]))));

        def result = Warehouse.getCollection().aggregate([grp,clickThroughRate]).iterator()

        def map = [:]

        result.each {

            if (map[it['_id'].datasource]) {

                map[it['_id'].datasource] << [campaign        : it._id.campaign, totalClicks: it.totalClicks,
                                              totalImpressions: it.totalImpressions, ctr: it.CTR.round(4)]
            } else {
                map[it['_id'].datasource] = [] << [campaign        : it._id.campaign, totalClicks: it.totalClicks,
                                                   totalImpressions: it.totalImpressions, ctr: it.CTR.round(4)]
            }
        }
        map
    }
}
