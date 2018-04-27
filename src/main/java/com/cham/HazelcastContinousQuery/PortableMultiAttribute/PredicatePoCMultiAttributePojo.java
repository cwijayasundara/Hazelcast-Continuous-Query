package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.QueryCacheConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.QueryCache;
import com.hazelcast.map.listener.*;
import com.hazelcast.query.SqlPredicate;

import java.util.Collection;
import java.util.Iterator;

public class PredicatePoCMultiAttributePojo {

    private final static String cacheName="employee-map-cache";
    //private final static String sqlPredicate="name like A% AND age>15 AND salary<600";

    private final static String sqlPredicate="name like A%";

    private final static String mapName="employee-map";
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

        IMap<String, Employee> clientEmpMap = (IMap) client.getMap(mapName);
        // continous query cache
        QueryCache<String, Employee> queryCache = clientEmpMap.getQueryCache(cacheName, new SqlPredicate(sqlPredicate),true);

        queryCache.addEntryListener(new EmployeeEntryListener(),
                                    new SqlPredicate("name like A%"), true);

        System.out.println("Registered the listener ..");

        Collection<Employee> employeeCollection = queryCache.values();

       while (true) {
            try {
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }

            int size = queryCache.size();
            System.out.println("Continuous query cache size = " + size);
            Iterator itr = employeeCollection.iterator();
            while(itr.hasNext()){
                System.out.println(itr.next());
            }
        }
    }

    static class EmployeeEntryListener
            implements EntryAddedListener<String, Employee>,
            EntryUpdatedListener<String, Employee>,
            EntryRemovedListener<String, Employee>,
            EntryEvictedListener<String, Employee>,
            MapEvictedListener, MapClearedListener {

        @Override
        public void entryAdded(EntryEvent<String, Employee> event) {
            System.out.println("entryAdded:" + event);
        }

        @Override
        public void entryRemoved(EntryEvent<String, Employee> event) {
            System.out.println("entryRemoved:" + event);
        }

        @Override
        public void entryUpdated(EntryEvent<String, Employee> event) {
            System.out.println("entryUpdated:" + event);
        }

        @Override
        public void entryEvicted(EntryEvent<String, Employee> event) {
            System.out.println("entryEvicted:" + event);
        }

        @Override
        public void mapEvicted(MapEvent event) {
            System.out.println("mapEvicted:" + event);
        }

        @Override
        public void mapCleared(MapEvent event) {
            System.out.println("mapCleared:" + event);

        }
    }
}
