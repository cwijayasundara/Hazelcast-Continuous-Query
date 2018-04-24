package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

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

public class PredicatePoCMultiAttributePojo {

    private final static String cacheName="employee-map-cache";
    private final static String sqlPredicate="name like A% AND age>15 AND salary<600";
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

        // create the IMap
        IMap<String, Employee> serverEmpMap = client.getMap(mapName);
        // populate data to the server Map
        serverEmpMap.set("Alice", new Employee("Alice", 20, 500.50 ));
        serverEmpMap.set("Bob", new Employee("Bob", 45, 1000.56 ));
        serverEmpMap.set("Sam", new Employee("Sam", 35, 4567.89));
        serverEmpMap.set("Tom", new Employee("Tom", 60, 5000.45));
        serverEmpMap.set("Antoney", new Employee("Antoney",80, 345.56 ));

        IMap<String, Employee> clientEmpMap = (IMap) client.getMap(mapName);

        QueryCache<String, Employee> queryCache = clientEmpMap.getQueryCache(cacheName, new SqlPredicate(sqlPredicate),true);
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
}
