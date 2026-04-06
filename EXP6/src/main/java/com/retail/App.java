package com.retail;

import com.retail.entity.Product;
import com.retail.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Initializing Hibernate setup...");
        
        // 1. Create Data
        System.out.println("\n--- 1. Creating Products ---");
        Product p1 = new Product("Laptop", "Gaming Laptop with 16GB RAM", 1200.00, 10);
        Product p2 = new Product("Mouse", "Wireless Gaming Mouse", 50.00, 50);
        Product p3 = new Product("Desktop", "Office PC", 800.00, 5);
        
        saveProduct(p1);
        saveProduct(p2);
        saveProduct(p3);

        // 2. Read All
        System.out.println("\n--- 2. Reading All Products ---");
        List<Product> products = getAllProducts();
        products.forEach(System.out::println);

        // 3. Update Data
        if (!products.isEmpty()) {
            System.out.println("\n--- 3. Updating First Product ---");
            Product productToUpdate = products.get(0);
            productToUpdate.setPrice(1100.00); // Apply discount
            productToUpdate.setDescription("Gaming Laptop with 32GB RAM");
            updateProduct(productToUpdate);
            
            // Read again to verify the update
            Product updatedProduct = getProductById(productToUpdate.getId());
            System.out.println("Updated Product: " + updatedProduct);
        }

        // 4. Delete Data
        if (products.size() > 1) {
            System.out.println("\n--- 4. Deleting Second Product ---");
            Product productToDelete = products.get(1);
            deleteProduct(productToDelete.getId());
            
            // Print remaining
            System.out.println("--- Remaining Products After Deletion ---");
            getAllProducts().forEach(System.out::println);
        }

        // Clean up resources
        HibernateUtil.shutdown();
        System.out.println("\nOperations Complete.");
    }

    public static void saveProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            System.out.println("Saved: " + product.getName());
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try { transaction.rollback(); } catch (Exception ex) {}
            }
            e.printStackTrace();
        }
    }

    public static Product getProductById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Product.class, id);
        }
    }

    public static List<Product> getAllProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Product", Product.class).list();
        }
    }

    public static void updateProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try { transaction.rollback(); } catch (Exception ex) {}
            }
            e.printStackTrace();
        }
    }

    public static void deleteProduct(Long id) {
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
            if (transaction != null && transaction.isActive()) {
                try { transaction.rollback(); } catch (Exception ex) {}
            }
            e.printStackTrace();
        }
    }
}
