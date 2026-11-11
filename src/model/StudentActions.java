package model;

import java.io.IOException;
import java.util.List;
/**

* Defines the core operations supported by the

** Student Support Management System.

*/
public interface StudentActions {
    boolean addStudent(Student student);
    List<Student> getAllStudents();
    Student findStudentById(int studentId);
    boolean updateStudent(int studentId, String name, String programme, String supportCategory);
    boolean deleteStudent(int studentId);
    void saveStudentsToFile() throws IOException;
    int loadStudentsFromFile() throws IOException;
}
