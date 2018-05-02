package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

// This will produce values to a
public class ValueProducerMain {

    private final static String mapName="employee-map";

    public static void main(String args[]){

        ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        // create the IMap
        IMap<String, Employee> serverEmpMap = client.getMap(mapName);
        // populate data to the server Map
        serverEmpMap.set("Alice", new Employee("Alice", 20, 500.50 ));
        serverEmpMap.set("Bob", new Employee("Bob", 45, 1000.56 ));
        serverEmpMap.set("Sam", new Employee("Sam", 35, 4567.89));
        serverEmpMap.set("Tom", new Employee("Tom", 60, 5000.45));
        serverEmpMap.set("Antoney", new Employee("Antoney",80, 345.56 ));
        serverEmpMap.set("Alice", new Employee("Alice", 50, 5000.50 ));
        serverEmpMap.set("Adam", new Employee("Adam", 20, 5000.50 ));
        serverEmpMap.set("Alice", new Employee("Alice", 22, 345.00 ));

        serverEmpMap.remove("Alice");
    }
}
