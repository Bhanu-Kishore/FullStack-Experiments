package com.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class App {
    public static void main(String[] args) {
        // Create - Insert a new product
        Long productId = createProduct("Laptop", 1200.50, 10);
        System.out.println("--- Product Created ---");
        
        // Read - Fetch the inserted product
        readProduct(productId);
        System.out.println("--- Product Read ---");
        
        // Update - Modify the product price
        updateProduct(productId, 1100.00);
        System.out.println("--- Product Updated ---");
        
        // Delete - Remove the product
        deleteProduct(productId);
        System.out.println("--- Product Deleted ---");
        
        // Close session factory
        HibernateUtil.getSessionFactory().close();
    }
    
    private static Long createProduct(String name, Double price, Integer quantity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Long productId = null;
        try {
            transaction = session.beginTransaction();
            Product product = new Product(name, price, quantity);
            session.persist(product);
            transaction.commit();
            productId = product.getId();
            System.out.println("Created Product: " + product);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productId;
    }
    
    private static void readProduct(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Product product = session.get(Product.class, id);
            System.out.println("Read Product: " + product);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    private static void updateProduct(Long id, Double newPrice) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                product.setPrice(newPrice);
                session.merge(product);
                System.out.println("Updated Product: " + product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    private static void deleteProduct(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.remove(product);
                System.out.println("Deleted Product ID: " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
