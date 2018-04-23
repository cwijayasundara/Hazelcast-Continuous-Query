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

        empMap.set("Alice", new Employee("Alice","20", "500.36" ));
        empMap.set("Bob", new Employee("Bob", "40", "200.50"));
        empMap.set("Sam", new Employee("Sam", "60", "1000.00"));
        empMap.set("Tom", new Employee("Tom", "35", "750.00"));
        empMap.set("Antoney", new Employee("Antoney","45", "300.89" ));

        Collection<Employee> employees = empMap.values(new SqlPredicate( "name like A% OR name like S%"));

        Iterator itr = employees.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
