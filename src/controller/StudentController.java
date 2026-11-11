package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import model.Student;
import model.StudentRepository;
import view.StudentView;

public class StudentController {

    private final StudentRepository repository;
    private final StudentView view;
    private boolean running;

    public StudentController(
            StudentRepository repository,
            StudentView view
    ) {
        this.repository = repository;
        this.view = view;
    }

    public void run() {
        running = true;
        view.displayHeading();

        while (running) {
            view.displayMenu();

            String selection = view.readInput(
                    "Select an option: "
            );

            handleMenuSelection(selection);
        }

        view.close();
    }

    private void handleMenuSelection(String selection) {
        switch (selection) {
            case "1" -> addStudent();

            case "2" ->
                view.displayStudents(
                        repository.getAllStudents()
                );

            case "3" -> updateStudent();

            case "4" -> deleteStudent();

            case "5" -> saveStudents();

            case "6" -> loadStudents();

            case "0" -> exitSystem();

            default ->
                view.displayError(
                        "Incorrect menu selection. Choose 0 to 6."
                );
        }
    }

    private void addStudent() {
        int studentId = view.readPositiveInt(
                "Student ID: "
        );

        if (repository.findStudentById(studentId) != null) {
            view.displayError(
                    "A student with ID "
                            + studentId
                            + " already exists."
            );
            return;
        }

        String name = view.readRequiredText(
                "Student name: "
        );

        String programme = view.readRequiredText(
                "Programme: "
        );

        String supportCategory = view.readRequiredText(
                "Support category: "
        );

        try {
            Student student = new Student(
                    studentId,
                    name,
                    programme,
                    supportCategory
            );

            boolean added = repository.addStudent(student);

            if (added) {
                view.displayMessage(
                        "Student added successfully."
                );
            } else {
                view.displayError(
                        "The student could not be added."
                );
            }
        } catch (IllegalArgumentException exception) {
            view.displayError(
                    exception.getMessage()
            );
        }
    }

    private void updateStudent() {
        int studentId = view.readPositiveInt(
                "Enter the student ID to update: "
        );

        if (repository.findStudentById(studentId) == null) {
            view.displayError(
                    "Student ID "
                            + studentId
                            + " was not found."
            );
            return;
        }

        String name = view.readRequiredText(
                "New student name: "
        );

        String programme = view.readRequiredText(
                "New programme: "
        );

        String supportCategory = view.readRequiredText(
                "New support category: "
        );

        try {
            repository.updateStudent(
                    studentId,
                    name,
                    programme,
                    supportCategory
            );

            view.displayMessage(
                    "Student updated successfully."
            );
        } catch (IllegalArgumentException exception) {
            view.displayError(
                    exception.getMessage()
            );
        }
    }

    private void deleteStudent() {
        int studentId = view.readPositiveInt(
                "Enter the student ID to delete: "
        );

        boolean deleted =
                repository.deleteStudent(studentId);

        if (deleted) {
            view.displayMessage(
                    "Student deleted successfully."
            );
        } else {
            view.displayError(
                    "Student ID "
                            + studentId
                            + " was not found."
            );
        }
    }

    private void saveStudents() {
        try {
            repository.saveStudentsToFile();

            view.displayMessage(
                    "Students saved successfully to: "
                            + repository.getFilePath()
            );
        } catch (IOException exception) {
            view.displayError(
                    "The file could not be saved: "
                            + exception.getMessage()
            );
        }
    }

    private void loadStudents() {
        try {
            int numberLoaded =
                    repository.loadStudentsFromFile();

            view.displayMessage(
                    numberLoaded
                            + " student record(s) "
                            + "loaded successfully."
            );

            view.displayStudents(
                    repository.getAllStudents()
            );
        } catch (FileNotFoundException exception) {
            view.displayError(
                    "File not found: "
                            + exception.getMessage()
            );
        } catch (IOException exception) {
            view.displayError(
                    "The file could not be read: "
                            + exception.getMessage()
            );
        }
    }

    private void exitSystem() {
        running = false;

        view.displayMessage(
                "System closed. "
                        + "Remember to save any changes first."
        );
    }
}