package com.jryyy.forum;

public class NameEntity {
    private String name;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NameEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}