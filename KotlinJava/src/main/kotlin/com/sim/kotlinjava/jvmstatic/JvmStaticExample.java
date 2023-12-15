package com.sim.kotlinjava.jvmstatic;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

public class JvmStaticExample {
    public static void main(String[] args) {
//        final String hello = HelloClass.Companion.hello();

//        final String hi = HiObject.INSTANCE.hi();

        String hello = HelloClass.hello();

        String hi = HiObject.hi();
    }
}
