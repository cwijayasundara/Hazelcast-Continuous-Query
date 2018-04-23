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
    private int age;
    private Double salary;

    public Employee(String name, int age, Double salary ) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
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
        writer.writeInt("age", age);
        writer.writeDouble("salary", salary);
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        System.out.println("Deserialize");
        this.name = reader.readUTF("name");
        this.age = reader.readInt("age");
        this.salary=reader.readDouble("salary");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}