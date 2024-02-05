package org.soa;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class FlowerCustomerApplication {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}