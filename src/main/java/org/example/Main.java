
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PASSWORD = "system";

    static Scanner sc = new Scanner(System.in);

    // Database connection
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 1. Get all records
    public static void getAll() throws Exception {

        String sql = "SELECT * FROM maniprasad";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println(
                        "ID: " + rs.getLong("adhaar") +
                                ", Name: " + rs.getString("name") +
                                ", Age: " + rs.getInt("age") +
                                ", Gender: " + rs.getString("gender") +
                                ", Address: " + rs.getString("address")
                );
            }

            if (!found) {
                System.out.println("No records found!");
            }
        }
    }

    // 2. Get record by ID
    public static void getById() throws Exception {

        System.out.print("Enter ID to search: ");
        long id = sc.nextLong();
        sc.nextLine();

        String sql = "SELECT * FROM maniprasad WHERE adhaar = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println(
                            "ID: " + rs.getLong("adhaar") +
                                    ", Name: " + rs.getString("name") +
                                    ", Age: " + rs.getInt("age") +
                                    ", Gender: " + rs.getString("gender") +
                                    ", Address: " + rs.getString("address")
                    );
                } else {
                    System.out.println("Record not found!");
                }
            }
        }
    }

    // 3. Insert record
    public static void insert() throws Exception {

        System.out.print("Enter ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter address: ");
        String address = sc.nextLine();

        String sql = "INSERT INTO maniprasad " +
                "(adhaar, name, age, gender, address) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, gender);
            ps.setString(5, address);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Record inserted successfully!");
            }
        }
    }

    // 4. Update record
    public static void update() throws Exception {

        System.out.print("Enter ID of record to update: ");
        long id = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Enter new age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter new address: ");
        String address = sc.nextLine();

        String sql = "UPDATE maniprasad SET name = ?, age = ?, " +
                "gender = ?, address = ? WHERE adhaar = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, address);
            ps.setLong(5, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Record updated successfully!");
            } else {
                System.out.println("Record not found!");
            }
        }
    }

    // 5. Delete record
    public static void delete() throws Exception {

        System.out.print("Enter ID of record to delete: ");
        long id = sc.nextLong();
        sc.nextLine();

        String sql = "DELETE FROM maniprasad WHERE adhaar = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Record deleted successfully!");
            } else {
                System.out.println("Record not found!");
            }
        }
    }

    // Main menu
    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== STUDENT CRUD MENU =====");
            System.out.println("1. Get All Records");
            System.out.println("2. Get Record By ID");
            System.out.println("3. Insert Record");
            System.out.println("4. Update Record");
            System.out.println("5. Delete Record");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {

                    case 1:
                        getAll();
                        break;

                    case 2:
                        getById();
                        break;

                    case 3:
                        insert();
                        break;

                    case 4:
                        update();
                        break;

                    case 5:
                        delete();
                        break;

                    case 6:
                        System.out.println("Program ended.");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
