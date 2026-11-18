package com.saulo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductService {

    Map<String, Product> products = new HashMap<>();
    private final Lock lock = new ReentrantLock();

    public void add(String name, int quantity) {
        Product product = new Product(name, quantity);
        product.setId(products.size() + 1);

        products.put(product.getName(), product);
    }

    public boolean buy(String name, int quantity) {
        lock.lock(); // trava; apenas uma thread por vez pode executar
        try {
            Product product = products.get(name);

            if (product.getQuantity() >= quantity) {
                // simula um atraso de processamento
                try { Thread.sleep(10); } catch (InterruptedException e) {}

                product.setQuantity(product.getQuantity() - quantity);
                return true;
            }
            return false;

        } finally {
            lock.unlock(); // destrava
        }
    }
}
