package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.nio.serialization.VersionedPortable;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Employee implements Serializable, VersionedPortable {

    Employee() {
    }

    private final String id = UUID.randomUUID().toString();

    private String name;
    private int age;
    private Double salary;
    private Double height;
    private EmployeeAddress currentAddress;
    private List<EmployeeAddress> pastAddress = new ArrayList<>();

    private EmployeeAddress address;

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", height=" + height +
                ", currentAddress=" + currentAddress +
                ", pastAddress=" + pastAddress +
                '}';
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Employee(String name, int age, Double salary, Double height, EmployeeAddress currentAddress, List<EmployeeAddress> pastAddress) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.height = height;
        this.currentAddress = currentAddress;
        this.pastAddress = pastAddress;
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
        System.out.println("Serialize " + name);

        writer.writeUTF("name", name);
        writer.writeInt("age", age);
        writer.writeDouble("salary", salary);
        writer.writeDouble("height", height);

        if (this.currentAddress != null) {
            writer.writePortable("currentAddress", this.address);
        } else {
            writer.writePortable("currentAddress", new EmployeeAddress());
        }

        if (this.pastAddress == null || this.pastAddress.isEmpty()) {
            this.pastAddress = new ArrayList<>();
            this.pastAddress.add(new EmployeeAddress());
        }
        writer.writePortableArray("pastAddress", this.pastAddress.toArray(new Portable[this.pastAddress.size()]));

    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        System.out.println("Deserialize");
        this.name = reader.readUTF("name");
        this.age = reader.readInt("age");
        this.salary = reader.readDouble("salary");
        this.height = reader.readDouble("height");
        this.currentAddress = reader.readPortable("currentAddress");

        Portable[] pastAddresses = reader.readPortableArray("pastAddress");
        for (Portable portable : pastAddresses) {
            if (portable != null) {
                this.pastAddress.add((EmployeeAddress) portable);
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassVersion() {
        return 1;
    }
}