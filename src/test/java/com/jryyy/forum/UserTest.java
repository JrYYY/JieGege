package com.jryyy.forum;

import java.time.LocalDateTime;

public class UserTest {

    public static void main(String[] args) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);
        System.out.println(startDate);
        System.out.println(endDate);
    }
}
