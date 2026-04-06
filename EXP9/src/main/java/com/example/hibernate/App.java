package com.example.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting Application...");

        // Create
        createProduct("Laptop", 999.99, 10);
        createProduct("Smartphone", 599.50, 20);

        // Read
        readProducts();

        // Update
        updateProduct(1, 899.99);

        // Read after update
        readProducts();

        // Delete
        deleteProduct(2);

        // Final Read
        readProducts();
        
        System.out.println("Application Finished. Shutting down...");
        System.exit(0);
    }

    public static void createProduct(String name, double price, int quantity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = new Product(name, price, quantity);
            session.save(product);
            transaction.commit();
            System.out.println("Product created: " + product);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void readProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Product> products = session.createQuery("from Product", Product.class).list();
            System.out.println("\n--- Product List ---");
            for (Product product : products) {
                System.out.println(product);
            }
            System.out.println("--------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct(int id, double newPrice) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                product.setPrice(newPrice);
                session.update(product);
                System.out.println("Product updated: " + product);
            } else {
                System.out.println("Product with ID " + id + " not found for update.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                System.out.println("Product deleted: " + product);
            } else {
                System.out.println("Product with ID " + id + " not found for deletion.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
