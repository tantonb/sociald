package com.tantonb.sociald;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Test {

    public static void useConsumer(Consumer<String> c) {
        c.accept("Hello World");
    }

    public static void useSupplier(Supplier<String> s) {
        System.out.println(s.get());
    }

    public static void main(String[] args) {

        Consumer<String> c = s -> System.out.println(s);
        Supplier<String> s = () -> "Hello World";

        useConsumer(c);
        useSupplier(s);
        useConsumer(s2 -> useSupplier(() -> s2));

        String s2 = "asdf:xyz";
        int i = s2.indexOf(":");
        System.out.println(s2.substring(i + 1));
        System.out.println(s2.substring(i + 1, s2.length()));
    }
}
