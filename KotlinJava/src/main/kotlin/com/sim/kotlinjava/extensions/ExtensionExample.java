package com.sim.kotlinjava.extensions;

public class ExtensionExample {
    public static void main(String[] args) {
//        "ABCD".first();
//        "ABCD".addFirst('Z');

        System.out.println(KotlinExtensionKt.first("ABCD"));
        System.out.println(KotlinExtensionKt.addFirst("ABCD",'Z'));
    }
}
