package app;

import controller.StudentController;
import model.StudentRepository;
import view.StudentView;

/** Application entry point. */
public class Main {
    public static void main(String[] args) {
        StudentRepository repository = new StudentRepository("students.txt");
        StudentView view = new StudentView();
        StudentController controller = new StudentController(repository, view);
        controller.run();
    }
}
