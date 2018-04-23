package com.cham.HazelcastContinousQuery.Predicates;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

import java.util.Collection;
import java.util.Iterator;

public class PredicatePoC {

    public static void main(String args[]){

       /* ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);*/ //does not work

        HazelcastInstance client = Hazelcast.newHazelcastInstance();

        IMap<String, Employee> empMap = client.getMap("employee-map");

        empMap.set("Alice", new Employee("Alice", 35, true, 500.50));
        empMap.set("Bob", new Employee("Bob", 54, false, 1000.45));
        empMap.set("Sam", new Employee("Sam", 20, true, 100.45));
        empMap.set("Tom", new Employee("Tom", 21, false, 234.56));
        empMap.set("Antoney", new Employee("Antoney", 60, true, 23.45));

        //Collection<Employee> employees = empMap.values(new SqlPredicate( "__key like A%")); // working

        Collection<Employee> employees = empMap.values(new SqlPredicate( "name like A% and active=true"));

       /* EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.is( "active" ).and( e.get( "age" ).lessThan(30));
        Collection<Employee> employees = empMap.values( predicate );*/

        Iterator itr = employees.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
