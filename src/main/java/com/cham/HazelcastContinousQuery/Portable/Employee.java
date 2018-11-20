package com.cham.HazelcastContinousQuery.Portable;

import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.nio.serialization.VersionedPortable;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public class Employee  implements Serializable, VersionedPortable {

    Employee(){}

    private final String id = UUID.randomUUID().toString();

    private String name;
    private int age;
    private String country;

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Employee(String name, int age, String country) {
        this.name = name;
        this.age = age;
        this.country = country;
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
    public int getClassVersion(){
        return 2;
    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {
        System.out.println("Serialize");
        writer.writeUTF("name", name);
        writer.writeInt("age", age);
        writer.writeUTF("country", country);
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        System.out.println("Deserialize");
        this.name = reader.readUTF("name");
        this.age = reader.readInt("age");
        this.country = reader.readUTF("country");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}