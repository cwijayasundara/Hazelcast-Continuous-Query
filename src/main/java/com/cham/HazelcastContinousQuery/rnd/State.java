package com.cham.HazelcastContinousQuery.rnd;

import com.cham.HazelcastContinousQuery.PortableMultiAttribute.PortableFactoryImpl;
import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public class State implements Serializable, Portable {

    @Override
    public String toString() {
        return "State{" +
                "id='" + id + '\'' +
                ", stateId='" + stateId + '\'' +
                ", stateName='" + stateName + '\'' +
                ", stateDescription='" + stateDescription + '\'' +
                '}';
    }

    private final String id = UUID.randomUUID().toString();
    private String stateId;
    private String stateName;
    private String stateDescription;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public State(){

    }

    public State(String stateId, String stateName, String stateDescription){
        this.stateId = stateId;
        this.stateName=stateName;
        this.stateDescription= stateDescription;
    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {
        System.out.println("Serialize State..");
        writer.writeUTF("stateId", stateId );
        writer.writeUTF("stateName", stateName);
        writer.writeUTF("stateDescription", stateDescription);
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        System.out.println("Deserialize State..");
        this.stateId = reader.readUTF("stateId");
        this.stateName = reader.readUTF("stateName");
        this.stateDescription=reader.readUTF("stateDescription");
    }

    @Override
    public int getClassId() {
        return PortableFactoryImpl.STATE_CLASS_ID;
    }

    @Override
    public int getFactoryId() {
        return PortableFactoryImpl.FACTORY_ID;
    }
}
