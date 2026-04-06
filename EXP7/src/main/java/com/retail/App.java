package com.retail;

import com.retail.entity.Product;
import com.retail.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Create
        Long productId = createProduct("Laptop", 1200.50, 10);
        System.out.println("Created Product with ID: " + productId);

        // Read (All)
        System.out.println("All Products:");
        readAllProducts().forEach(System.out::println);

        // Read (Single)
        System.out.println("Reading Product " + productId + ":");
        Product p = readProduct(productId);
        System.out.println(p);

        // Update
        if (p != null) {
            System.out.println("Updating Product " + productId + " price and quantity...");
            updateProduct(productId, 1100.00, 15);
        }

        // Read after update
        System.out.println("Product after update:");
        System.out.println(readProduct(productId));

        // Delete
        System.out.println("Deleting Product " + productId + "...");
        deleteProduct(productId);

        // Final Read (All)
        System.out.println("All Products after deletion:");
        readAllProducts().forEach(System.out::println);

        // Clean up
        HibernateUtil.shutdown();
    }

    private static Long createProduct(String name, double price, int quantity) {
        Transaction transaction = null;
        Session session = null;
        Long id = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Product product = new Product(name, price, quantity);
            id = (Long) session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return id;
    }

    private static Product readProduct(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static List<Product> readAllProducts() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("from Product", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void updateProduct(Long id, double newPrice, int newQuantity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                product.setPrice(newPrice);
                product.setQuantity(newQuantity);
                session.update(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void deleteProduct(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
