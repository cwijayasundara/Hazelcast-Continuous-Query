package com.cham.HazelcastContinousQuery.Portable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class PredicatePoCSingleAttributePojo {

    public static void main(String args[]){

        ClientConfig clientConfig = new ClientConfig();
        SerializationConfig srzConfig = clientConfig.getSerializationConfig();
        srzConfig.addPortableFactoryClass(1, "com.cham.HazelcastContinousQuery.Portable.PortableFactoryImpl");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, Employee> empMap = client.getMap("employee-map");
        IMap<String, Map<String, Employee>> empMap2 = client.getMap("employee-map");

        empMap.set("Alexandra", new Employee("Alexandra", 45, "UK"));
        empMap.set("Bobby", new Employee("Bobby", 25, "India"));
        empMap.set("Sammy", new Employee("Sammy", 35, "Canada"));
        empMap.set("Thompson", new Employee("Thompson", 45, "US"));
        empMap.set("Amalie", new Employee("Amalie", 65, "SL"));
        empMap.set("Alice", new Employee("Alice", 20, "XXXX"));

        Collection<Employee> employees = empMap.values(new SqlPredicate( "name like A% OR name like B%"));

        Iterator itr = employees.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
