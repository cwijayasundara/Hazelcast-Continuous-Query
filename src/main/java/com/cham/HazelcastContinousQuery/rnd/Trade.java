package com.cham.HazelcastContinousQuery.rnd;

import com.cham.HazelcastContinousQuery.PortableMultiAttribute.PortableFactoryImpl;
import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Trade implements Serializable,Portable {

    @Override
    public String toString() {
        return "Trade{" +
                "id='" + id + '\'' +
                ", tradeId='" + tradeId + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", tradeValue=" + tradeValue +
                ", states=" + states +
                '}';
    }

    private final String id = UUID.randomUUID().toString();
    private String tradeId;
    private String tradeName;
    private int tradeValue;

    private List<State> states = new ArrayList<>();

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public int getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(int tradeValue) {
        this.tradeValue = tradeValue;
    }

    public List<State> getState() {
        return states;
    }

    public void setState(List<State> state) {
        this.states = state;
    }

    public Trade(){}

    public Trade(String tradeId, String tradeName, int tradeValue, ArrayList<State> state ){
        this.tradeId=tradeId;
        this.tradeName=tradeName;
        this.tradeValue= tradeValue;
        this.states=state;
    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {
        System.out.println("Serialize Trade..");
        writer.writeUTF("tradeId", tradeId );
        writer.writeUTF("tradeName", tradeName);
        writer.writeInt("tradeValue", tradeValue);

        if(states != null && !states.isEmpty()) {
            writer.writePortableArray("states", states.toArray(new Portable[states.size()]));
            writer.writeBoolean("_has__listProperty", true);
        }else{
            writer.writeBoolean("_has__listProperty", false);
        }

    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        System.out.println("Deserialize Trade..");
        this.tradeId = reader.readUTF("tradeId");
        this.tradeName = reader.readUTF("tradeName");
        this.tradeValue=reader.readInt("tradeValue");

        if(reader.readBoolean("_has__listProperty")) {
            Portable[] listPropertyArr = reader.readPortableArray("states");
            for (Portable p : listPropertyArr) {
                states.add((State) p);
            }
        }
    }

    @Override
    public int getClassId() {
        return PortableFactoryImpl.TRADE_CLASS_ID;
    }

    @Override
    public int getFactoryId() {
        return PortableFactoryImpl.FACTORY_ID;
    }

}
