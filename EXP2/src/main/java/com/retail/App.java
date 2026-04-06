package com.retail;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.retail.entity.Product;
import com.retail.util.HibernateUtil;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("--- Starting Hibernate CRUD Operations ---");

        System.out.println("\n[1] Inserting new products...");
        int p1Id = addProduct("Laptop", "High-performance gaming laptop", 1200.50, 10);
        int p2Id = addProduct("Smartphone", "Latest gen 5G smartphone", 800.00, 20);
        int p3Id = addProduct("Headphones", "Noise-cancelling wireless headphones", 150.00, 50);

        System.out.println("\n[2] Retrieving product with ID " + p1Id + "...");
        getProduct(p1Id);

        System.out.println("\n[3] Updating quantity and price of product with ID " + p2Id + "...");
        updateProduct(p2Id, 750.00, 15);
        getProduct(p2Id);
        System.out.println("\n[4] Deleting discontinued product with ID " + p3Id + "...");
        deleteProduct(p3Id);
        System.out.println("\n--- Remaining Products in Database ---");
        listAllProducts();

        HibernateUtil.getSessionFactory().close();
        System.out.println("\n--- Execution Completed ---");
    }

    private static int addProduct(String name, String description, double price, int quantity) {
        Transaction transaction = null;
        int productId = -1;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = new Product(name, description, price, quantity);
            session.save(product);
            productId = product.getId();
            transaction.commit();
            System.out.println("Inserted: " + product);
        } catch (Exception e) {
             if (transaction != null) {
                 transaction.rollback();
             }
             e.printStackTrace();
        }
        return productId;
    }

    private static void getProduct(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, id);
            if (product != null) {
                System.out.println("Retrieved: " + product);
            } else {
                System.out.println("Product not found with ID " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(int id, double newPrice, int newQuantity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                product.setPrice(newPrice);
                product.setQuantity(newQuantity);
                session.update(product);
                System.out.println("Successfully Updated Product ID " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                 transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void deleteProduct(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                System.out.println("Successfully Deleted Product ID " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                 transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void listAllProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Product> products = session.createQuery("from Product", Product.class).list();
            for (Product p : products) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
