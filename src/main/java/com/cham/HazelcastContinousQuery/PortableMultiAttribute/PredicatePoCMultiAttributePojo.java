package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

import java.util.Collection;
import java.util.Iterator;

public class PredicatePoCMultiAttributePojo {

    public static void main(String args[]){

        ClientConfig clientConfig = new ClientConfig();
        SerializationConfig srzConfig = clientConfig.getSerializationConfig();
        srzConfig.addPortableFactoryClass(1, "com.cham.HazelcastContinousQuery.PortableMultiAttribute.PortableFactoryImpl");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, Employee> empMap = client.getMap("employee-map");

        empMap.set("Alice", new Employee("Alice", 20, 500.50 ));
        empMap.set("Bob", new Employee("Bob", 45, 1000.56 ));
        empMap.set("Sam", new Employee("Sam", 35, 4567.89));
        empMap.set("Tom", new Employee("Tom", 60, 5000.45));
        empMap.set("Antoney", new Employee("Antoney",80, 345.56 ));

        Collection<Employee> employees = empMap.values(new SqlPredicate( "name like A% AND age<100 AND salary>500"));

        Iterator itr = employees.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
