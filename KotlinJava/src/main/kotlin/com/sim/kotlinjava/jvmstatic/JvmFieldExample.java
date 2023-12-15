package com.sim.kotlinjava.jvmstatic;

public class JvmFieldExample {
    public static void main(String[] args) {
//        JvmFieldClass.Companion.getId();
//
//        JvmFieldObject.INSTANCE.getName();

        int id = JvmFieldClass.id;
        String name = JvmFieldObject.name;

        int code = JvmFieldClass.CODE;

        String familyName = JvmFieldObject.NAME;
    }
}
