package com.cham.HazelcastContinousQuery.PortableMultiAttribute;

import com.cham.HazelcastContinousQuery.rnd.State;
import com.cham.HazelcastContinousQuery.rnd.Trade;
import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableFactory;

public class PortableFactoryImpl implements PortableFactory {

    public static final int EMPLOYEE_CLASS_ID = 1;
    public static final int STATE_CLASS_ID=2;
    public static final int TRADE_CLASS_ID=3;
    public static final int ADDRESS_CLASS_ID=4;

    public static final int FACTORY_ID = 1;

    public Portable create(int classId) {
        switch (classId) {
            case EMPLOYEE_CLASS_ID:
                return new Employee();
            case STATE_CLASS_ID:
                return new State();
            case TRADE_CLASS_ID:
                return new Trade();
            case ADDRESS_CLASS_ID:
                return new EmployeeAddress();
            default:
                return null;
        }
    }
}
