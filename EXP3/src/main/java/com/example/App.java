package com.example;

import com.example.entity.Product;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class App {

    public static void main(String[] args) {
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            // Task 2: Add 5-8 additional Product records into the database
            Transaction transaction = session.beginTransaction();
            session.persist(new Product("Laptop", 1200.50, 10, "Electronics"));
            session.persist(new Product("Smartphone", 800.00, 25, "Electronics"));
            session.persist(new Product("Desk Chair", 150.75, 5, "Furniture"));
            session.persist(new Product("Notebook", 2.50, 100, "Stationery"));
            session.persist(new Product("Headphones", 199.99, 15, "Electronics"));
            session.persist(new Product("Monitor", 300.00, 0, "Electronics"));
            session.persist(new Product("Dining Table", 500.00, 2, "Furniture"));
            session.persist(new Product("Pen", 1.50, 200, "Stationery"));
            transaction.commit();

            System.out.println("========== Records Inserted ==========");

            // Task 3: Retrieve all products sorted by price
            // a. Ascending order
            System.out.println("\n========== 3a. Products Sorted by Price (Ascending) ==========");
            Query<Product> query3a = session.createQuery("FROM Product ORDER BY price ASC", Product.class);
            query3a.getResultList().forEach(System.out::println);

            // b. Descending order
            System.out.println("\n========== 3b. Products Sorted by Price (Descending) ==========");
            Query<Product> query3b = session.createQuery("FROM Product ORDER BY price DESC", Product.class);
            query3b.getResultList().forEach(System.out::println);

            // Task 4: Sort products by quantity (highest first)
            System.out.println("\n========== 4. Products Sorted by Quantity (Highest First) ==========");
            Query<Product> query4 = session.createQuery("FROM Product ORDER BY quantity DESC", Product.class);
            query4.getResultList().forEach(System.out::println);

            // Task 5: Pagination
            // a. First 3 products
            System.out.println("\n========== 5a. First 3 Products ==========");
            Query<Product> query5a = session.createQuery("FROM Product ORDER BY id ASC", Product.class);
            query5a.setMaxResults(3);
            query5a.getResultList().forEach(System.out::println);

            // b. Next 3 products
            System.out.println("\n========== 5b. Next 3 Products ==========");
            Query<Product> query5b = session.createQuery("FROM Product ORDER BY id ASC", Product.class);
            query5b.setFirstResult(3); // Start from index 3 (4th element)
            query5b.setMaxResults(3);
            query5b.getResultList().forEach(System.out::println);

            // Task 6: Aggregate operations
            // a. Count total number of products
            System.out.println("\n========== 6a. Total Number of Products ==========");
            Long totalProducts = session.createQuery("SELECT COUNT(p) FROM Product p", Long.class).uniqueResult();
            System.out.println("Total Products: " + totalProducts);

            // b. Count products where quantity > 0
            System.out.println("\n========== 6b. Total Products with Quantity > 0 ==========");
            Long inStock = session.createQuery("SELECT COUNT(p) FROM Product p WHERE p.quantity > 0", Long.class).uniqueResult();
            System.out.println("Products with Quantity > 0: " + inStock);

            // c. Count products grouped by description
            System.out.println("\n========== 6c. Count Products Grouped by Description ==========");
            List<Object[]> groupedCounts = session.createQuery("SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description", Object[].class).getResultList();
            for (Object[] row : groupedCounts) {
                System.out.println("Description: " + row[0] + ", Count: " + row[1]);
            }

            // d. Find minimum and maximum price
            System.out.println("\n========== 6d. Minimum and Maximum Price ==========");
            Object[] minMaxPrice = session.createQuery("SELECT MIN(p.price), MAX(p.price) FROM Product p", Object[].class).uniqueResult();
            System.out.println("Minimum Price: " + minMaxPrice[0] + ", Maximum Price: " + minMaxPrice[1]);

            // Task 7: Query using GROUP BY to group products by description
            System.out.println("\n========== 7. Group By Description (Total Quantity per Category) ==========");
            List<Object[]> qtyPerDesc = session.createQuery("SELECT p.description, SUM(p.quantity) FROM Product p GROUP BY p.description", Object[].class).getResultList();
            for (Object[] row : qtyPerDesc) {
                System.out.println("Category: " + row[0] + " | Total Quantity: " + row[1]);
            }

            // Task 8: Query using WHERE to filter products within a price range
            System.out.println("\n========== 8. Products Filtered by Price Range (100 to 1000) ==========");
            Query<Product> query8 = session.createQuery("FROM Product p WHERE p.price BETWEEN 100 AND 1000", Product.class);
            query8.getResultList().forEach(System.out::println);

            // Task 9: Queries using LIKE
            // a. Names starting with certain letters
            System.out.println("\n========== 9a. Names starting with 'M' ==========");
            Query<Product> query9a = session.createQuery("FROM Product p WHERE p.name LIKE 'M%'", Product.class);
            query9a.getResultList().forEach(System.out::println);

            // b. Names ending with certain letters
            System.out.println("\n========== 9b. Names ending with 'r' ==========");
            Query<Product> query9b = session.createQuery("FROM Product p WHERE p.name LIKE '%r'", Product.class);
            query9b.getResultList().forEach(System.out::println);

            // c. Names containing a pattern anywhere (substring)
            System.out.println("\n========== 9c. Names containing 'book' ==========");
            Query<Product> query9c = session.createQuery("FROM Product p WHERE p.name LIKE '%book%'", Product.class);
            query9c.getResultList().forEach(System.out::println);

            // d. Names with an exact character length (e.g. 3 chars for 'Pen')
            System.out.println("\n========== 9d. Names with exact character length (3 characters) ==========");
            Query<Product> query9d = session.createQuery("FROM Product p WHERE p.name LIKE '___'", Product.class);
            query9d.getResultList().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
