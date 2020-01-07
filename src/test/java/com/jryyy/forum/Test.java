package com.jryyy.forum;

import org.springframework.util.StringUtils;

public class Test {
    @org.junit.Test
    public void test() {
        System.out.println(  StringUtils.replace("1010","/","%2F"));
        String destination = "/topic/singleChat/message";
        destination = destination.startsWith("/") ? destination : "/" + destination;
        System.out.println(destination);
    }


}
