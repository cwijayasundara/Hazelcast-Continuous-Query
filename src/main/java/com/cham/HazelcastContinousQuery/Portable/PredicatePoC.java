package com.cham.HazelcastContinousQuery.Portable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

import java.util.Collection;
import java.util.Iterator;

public class PredicatePoC {

    public static void main(String args[]){

        ClientConfig clientConfig = new ClientConfig();
        SerializationConfig srzConfig = clientConfig.getSerializationConfig();
        srzConfig.addPortableFactoryClass(1, "com.cham.HazelcastContinousQuery.Portable.PortableFactoryImpl");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, Employee> empMap = client.getMap("employee-map");

        empMap.set("Alice", new Employee("Alice"));
        empMap.set("Bob", new Employee("Bob"));
        empMap.set("Sam", new Employee("Sam"));
        empMap.set("Tom", new Employee("Tom"));
        empMap.set("Antoney", new Employee("Antoney"));

        Collection<Employee> employees = empMap.values(new SqlPredicate( "name like A% OR name like S%"));

        Iterator itr = employees.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
