package com.student.management;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("studentDB");
        MongoCollection<Document> collection = database.getCollection("students");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter course: ");
                    String course = sc.nextLine();

                    Document student = new Document("name", name)
                            .append("age", age)
                            .append("course", course);
                    collection.insertOne(student);
                    System.out.println("✅ Student added successfully!");
                    break;

                case 2:
                    System.out.println("\nAll Students:");
                    for (Document doc : collection.find()) {
                        System.out.println(doc.toJson());
                    }
                    break;

                case 3:
                    System.out.print("Enter student name to update: ");
                    String updateName = sc.nextLine();
                    System.out.print("Enter new course: ");
                    String newCourse = sc.nextLine();
                    collection.updateOne(new Document("name", updateName),
                            new Document("$set", new Document("course", newCourse)));
                    System.out.println("✅ Student updated successfully!");
                    break;

                case 4:
                    System.out.print("Enter student name to delete: ");
                    String deleteName = sc.nextLine();
                    collection.deleteOne(new Document("name", deleteName));
                    System.out.println("❌ Student deleted successfully!");
                    break;

                case 5:
                    System.out.println("👋 Exiting...");
                    mongoClient.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("⚠ Invalid choice. Try again!");
            }
        }
    }
}
