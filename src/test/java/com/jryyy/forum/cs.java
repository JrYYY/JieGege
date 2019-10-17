package com.jryyy.forum;

import lombok.Getter;

@Getter
public class cs {
    private String name;
    private String pass;


    public cs(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public static String func(int n) {
        if (n > 100 || n < 0) {
            return "输入无效!";
        } else {
            String str = (n > 90) ? "分,属于A等" : (n > 60) ? "分,属于B等" : "分,属于C等";
            return n + str;
        }
    }

    public static csBuilder builder() {
        return new csBuilder();
    }

    static class csBuilder {
        String name;
        String pass;

        public csBuilder name(String name) {
            this.name = name;
            return this;
        }

        public csBuilder pass(String pass) {
            this.pass = pass;
            return this;
        }

        public cs build() {
            return new cs(name, pass);
        }
    }
}
