package com.saulo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static void main() throws InterruptedException {

        final String PRODUCT_NAME = "TECLADO_GAMER";

        ProductService productService = new ProductService();
        productService.add(PRODUCT_NAME, 10);

        int numberThreads = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(numberThreads);

        System.out.println("INICIANDO O TESTE...");

        AtomicInteger managedToBuy = new AtomicInteger(0);

        for (int i = 0; i < numberThreads; i++) {
            executorService.submit(() -> {
                boolean buy = productService.buy(PRODUCT_NAME, 1);
                if (buy) {
                    managedToBuy.incrementAndGet();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println(managedToBuy + " threads conseguiram comprar");
        System.out.println("Teste finalizado. Estoque: " + productService.products);
    }
}
