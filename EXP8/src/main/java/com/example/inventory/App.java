package com.example.inventory;

import com.example.inventory.entity.Product;
import com.example.inventory.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class App {
    public static void main(String[] args) {
        
        System.out.println("Starting Application...");
        insertSampleProducts();
        
        // --- Custom Query Implementations ---
        // 1. Fetching products by category
        System.out.println("\n--- Fetching products by category: 'Electronics' ---");
        fetchByCategory("Electronics");
        
        // 2. Fetching products by price range (between min and max)
        System.out.println("\n--- Fetching products with price between 100 and 600 ---");
        fetchByPriceBetween(100.0, 600.0);
        
        // 3. Sorting products by price
        System.out.println("\n--- Sorting all products by price ---");
        fetchSortedByPrice();
        
        // 4. Fetching products above a price value
        System.out.println("\n--- Fetching products above price: 700 ---");
        fetchAbovePrice(700.0);

        // --- Standard Full CRUD Operations ---
        System.out.println("\n--- Demonstrating Update & Delete for full CRUD ---");
        updateProductCategory(1L, "Premium Electronics");
        deleteProduct(2L);
        
        HibernateUtil.shutdown();
        System.out.println("\nApplication Finished.");
    }

    private static void insertSampleProducts() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Product p1 = new Product("Laptop", "Electronics", 1200.0, 10);
            Product p2 = new Product("Headphones", "Electronics", 150.0, 50);
            Product p3 = new Product("Smartphone", "Electronics", 800.0, 20);
            Product p4 = new Product("Coffee Maker", "Home Appliances", 90.0, 15);
            Product p5 = new Product("Desk Chair", "Furniture", 250.0, 5);

            session.persist(p1);
            session.persist(p2);
            session.persist(p3);
            session.persist(p4);
            session.persist(p5);

            transaction.commit();
            System.out.println("Sample products inserted.");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void fetchByCategory(String category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Product> products = session.createQuery("FROM Product p WHERE p.category = :category", Product.class)
                    .setParameter("category", category)
                    .list();
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    private static void fetchByPriceBetween(double min, double max) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Product> products = session.createQuery("FROM Product p WHERE p.price BETWEEN :min AND :max", Product.class)
                    .setParameter("min", min)
                    .setParameter("max", max)
                    .list();
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    private static void fetchSortedByPrice() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Product> products = session.createQuery("FROM Product p ORDER BY p.price ASC", Product.class)
                    .list();
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    private static void fetchAbovePrice(double minPrice) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Product> products = session.createQuery("FROM Product p WHERE p.price > :minPrice", Product.class)
                    .setParameter("minPrice", minPrice)
                    .list();
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }
    
    private static void updateProductCategory(Long id, String newCategory) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product p = session.get(Product.class, id);
            if (p != null) {
                p.setCategory(newCategory);
                session.merge(p);
                System.out.println("Product updated: " + p);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product p = session.get(Product.class, id);
            if (p != null) {
                session.remove(p);
                System.out.println("Product deleted: " + p);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
