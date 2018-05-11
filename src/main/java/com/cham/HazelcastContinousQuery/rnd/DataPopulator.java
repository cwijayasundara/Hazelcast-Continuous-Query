package com.cham.HazelcastContinousQuery.rnd;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.ArrayList;

public class DataPopulator {

    private final static String mapName="trade-map";

    public static void main(String args[]){

        ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        // create the IMap
        IMap<String, Trade> serverTradeMap = client.getMap(mapName);
        // populate data to the server Map
        State state1 = new State("1", "In Progress", "In progress with backend");
        State state3 = new State("3", "Rejected", "Rejected by the backend");

        State state2 = new State("2", "In Risk", "In risk with risk systems");
        State state4 = new State("4", "In Jeperdy", "In jeperdy at the backend");

        ArrayList states1 = new ArrayList();
        states1.add(state1);
        states1.add(state3);

        ArrayList states2 = new ArrayList();
        states2.add(state2);
        states2.add(state4);

        serverTradeMap.set("1", new Trade("1", "LBG_Lloyds", 10000, states1));
        serverTradeMap.set("2", new Trade("2", "LBG_Halifax", 2000, states2 ));
    }
}
