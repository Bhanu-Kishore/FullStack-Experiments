package com.example;

import com.example.entity.Product;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting Hibernate + MySQL CRUD application...");

        // 1. CREATE
        Product newProduct = new Product("Smartphone", 599.99, 50);
        createProduct(newProduct);
        Product laptop = new Product("Gaming Laptop", 1499.00, 15);
        createProduct(laptop);

        // 2. READ ALL
        List<Product> products = getAllProducts();
        System.out.println("\n--- All Products ---");
        for (Product p : products) {
            System.out.println(p);
        }

        // 3. READ BY ID & 4. UPDATE
        if (!products.isEmpty()) {
            int firstId = products.get(0).getId();
            Product product = getProductById(firstId);
            System.out.println("\n--- Product Before Update ---");
            System.out.println(product);

            if (product != null) {
                product.setPrice(product.getPrice() - 50); // Give a discount
                updateProduct(product);
                System.out.println("--- Product After Update ---");
                System.out.println(getProductById(firstId));
            }

            // 5. DELETE
            if (products.size() > 1) {
                int secondId = products.get(1).getId();
                System.out.println("\n--- Deleting Product with ID " + secondId + " ---");
                deleteProduct(secondId);
                System.out.println("Products remaining: " + getAllProducts().size());
            }
        }

        // Shutdown Hibernate
        HibernateUtil.shutdown();
    }

    public static void createProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            System.out.println("Created: " + product.getName());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static List<Product> getAllProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Product", Product.class).list();
        }
    }

    public static Product getProductById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Product.class, id);
        }
    }

    public static void updateProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
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
                System.out.println("Deleted Product ID: " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
