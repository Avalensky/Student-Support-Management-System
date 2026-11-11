package view;

import java.util.List;
import java.util.Scanner;
import model.Student;

/** Console View: receives input and presents information to the user. */
public class StudentView {
    private final Scanner scanner;

    public StudentView() {
        scanner = new Scanner(System.in);
    }

    public void displayHeading() {
        System.out.println("============================================");
        System.out.println("     STUDENT MANAGEMENT SYSTEM");
        System.out.println("============================================");
    }

    public void displayMenu() {
        System.out.println("\n1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Update student");
        System.out.println("4. Delete student");
        System.out.println("5. Save students to file");
        System.out.println("6. Load students from file");
        System.out.println("0. Exit");
    }

    public String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readPositiveInt(String prompt) {
        while (true) {
            String input = readInput(prompt);
            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    displayError("Please enter a positive number.");
                } else {
                    return value;
                }
            } catch (NumberFormatException ex) {
                displayError("Invalid input. Please enter a numeric ID.");
            }
        }
    }

    public String readRequiredText(String prompt) {
        while (true) {
            String value = readInput(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            displayError("This field may not be empty.");
        }
    }

    public void displayStudents(List<Student> students) {
        if (students.isEmpty()) {
            displayMessage("No student records are currently stored.");
            return;
        }
        System.out.println("\n--- STUDENT RECORDS ---");
        for (Student student : students) {
            // Person reference could call this method polymorphically.
            System.out.println(student.getDisplayDetails());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.out.println("ERROR: " + message);
    }

    public void close() {
        scanner.close();
    }
}
