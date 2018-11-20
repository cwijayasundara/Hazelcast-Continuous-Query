package com.cham.HazelcastContinousQuery.rnd;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.QueryCacheConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.QueryCache;
import com.hazelcast.query.SqlPredicate;

import java.util.Collection;
import java.util.Iterator;

public class PredicateWithCollectionInside {

    private final static String cacheName="trade-cache";

    private final static String sqlPredicate="tradeName like LBG% and states[any].stateName = 'In Progress'";
    //private final static String sqlPredicate="tradeName like LBG%";
    private final static String mapName="trade-map";
    private final static String portableClassName="com.cham.HazelcastContinousQuery.PortableMultiAttribute.PortableFactoryImpl";
    private final static int factoryId=1;

    public static void main(String args[]){

        // sql predicates
        ClientConfig clientConfig = new ClientConfig();
        SerializationConfig srzConfig = clientConfig.getSerializationConfig();
        srzConfig.addPortableFactoryClass(factoryId, portableClassName);

        // create the query cache
        QueryCacheConfig queryCacheConfig = new QueryCacheConfig(cacheName);
        clientConfig.addQueryCacheConfig(mapName, queryCacheConfig);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, Trade> clientTradeMap = (IMap) client.getMap(mapName);

        // continous query cache
        QueryCache<String, Trade> queryCache = clientTradeMap.getQueryCache(cacheName, new SqlPredicate(sqlPredicate),true);

        Collection<Trade> tradeCollection = queryCache.values();

        while (true) {
            try {
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }

            int size = queryCache.size();
            System.out.println("Continuous query cache size = " + size);
            Iterator itr = tradeCollection.iterator();
            while(itr.hasNext()){
                Trade trade = (Trade)itr.next();
                System.out.println(trade.toString());
            }
        }
    }
}
