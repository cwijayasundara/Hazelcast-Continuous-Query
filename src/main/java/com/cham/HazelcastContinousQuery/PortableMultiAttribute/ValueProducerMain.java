package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


// This will produce values to a
public class ValueProducerMain {

    private final static String mapName="employee-map";

    public static void main(String args[]){
        ClientConfig clientConfig = new ClientConfig();
        SerializationConfig srzConfig = clientConfig.getSerializationConfig();
        srzConfig.addPortableFactoryClass(PortableFactoryImpl.FACTORY_ID, PortableFactoryImpl.class);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        // create the IMap
        IMap<String, Employee> serverEmpMap = client.getMap(mapName);
//        serverEmpMap.clear();
        System.out.println("Map size, " + serverEmpMap.size());
        // populate data to the server Map
        serverEmpMap.put("Alice", new Employee("Alice", 20, 501.50, 5.0, null, null));

        List<EmployeeAddress> pastAddress = new ArrayList<>();
        pastAddress.add(new EmployeeAddress("past address", 32, LocalDateTime.now().minusYears(3), false));

        EmployeeAddress currentAddress = new EmployeeAddress("hello", 23, LocalDateTime.now().minusYears(1), true);

        serverEmpMap.put("Andy", new Employee("Andy", 45, 1000.56, 10.00, currentAddress, pastAddress));

        pastAddress.add(new EmployeeAddress("past address1", 332, LocalDateTime.now().minusYears(4), false));

        serverEmpMap.put("Aliyu", new Employee("Aliyu", 45, 1000.56, 10.00, currentAddress, pastAddress));
/*        serverEmpMap.set("Sam", new Employee("Sam", 35, 4567.89, null));
        serverEmpMap.set("Tom", new Employee("Tom", 60, 5000.45, 34.00));
        serverEmpMap.set("Antoney", new Employee("Antoney",80, 345.56, 20.0 ));*/

    }
}
