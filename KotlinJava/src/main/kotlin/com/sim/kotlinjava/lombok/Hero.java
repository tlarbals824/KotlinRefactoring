package com.sim.kotlinjava.lombok;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@ToString
//@EqualsAndHashCode
public class Hero {
//    @Getter
//    @Setter
    private String name;

//    @Getter
//    @Setter
    private int age;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hero hero = (Hero) o;

        if (getAge() != hero.getAge()) return false;
        if (getName() != null ? !getName().equals(hero.getName()) : hero.getName() != null) return false;
        return getAddress() != null ? getAddress().equals(hero.getAddress()) : hero.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getAge();
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }



    public static void main(String[] args) {
        Hero hero = new Hero();
        hero.setName("심규민");
        hero.setAge(24);
        hero.setAddress("영등포");
    }
}
