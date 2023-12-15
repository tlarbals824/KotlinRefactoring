package com.sim.kotlinjava.gettersetter;

import java.time.LocalDate;

public class JavaGetterSetterExample {
    public static void main(String[] args) {
        Student student = new Student();

//        student.setName("심규민");
        student.name = "심규민";
        student.setBirthDate(LocalDate.now());

//        System.out.println(student.getName());
        System.out.println(student.name);
        System.out.println(student.getBirthDate());

        // student.setAge(~);
        System.out.println(student.getAge());

        // student.setGrade(~);
        student.changeGrade("A");
        System.out.println(student.getGrade());


    }
}
