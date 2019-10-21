package com.jryyy.forum;

public class Test {
    @org.junit.Test
    public void test() {
        double a = 35;
        double b = 20;
        double c = a / b;
        System.out.println("c===>" + c);   //1.75
        System.out.println("c===>" + (int) Math.ceil(c)); //2.0
        System.out.println((int) Math.floor(c));  //1.0
    }
}
