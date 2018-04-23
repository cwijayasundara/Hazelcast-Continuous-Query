package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public class Employee implements Serializable,Portable {

    Employee(){}

    private final String id = UUID.randomUUID().toString();

    private String name;
    private String age;
    private String salary;

    public Employee(String name, String age, String salary ) {
        this.name = name;
        this.age=age;
        this.salary=salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", salary=" + salary +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public int getClassId() {
        return PortableFactoryImpl.EMPLOYEE_CLASS_ID;
    }

    @Override
    public int getFactoryId() {
        return PortableFactoryImpl.FACTORY_ID;
    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {
        System.out.println("Serialize");
        writer.writeUTF("name", name );
       /* writer.writeUTF("age", age);
        writer.writeUTF("salary", salary);*/
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        System.out.println("Deserialize");
        this.name = reader.readUTF("name");
        /*this.age = reader.readUTF("age");
        this.salary=reader.readUTF("salary");*/
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}