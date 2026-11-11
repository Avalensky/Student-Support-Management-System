package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentRepository implements StudentActions {
    private static final String DEFAULT_FILE_NAME = "students.txt";
    private static final String DELIMITER = "|";

    private final List<Student> students;
    private final Path filePath;

    public StudentRepository() {
        this(DEFAULT_FILE_NAME);
    }

    public StudentRepository(String fileName) {
        students = new ArrayList<>();
        filePath = Paths.get(fileName);
    }

    @Override
    public boolean addStudent(Student student) {
        if (student == null || findStudentById(student.getStudentId()) != null) {
            return false;
        }
        students.add(student);
        return true;
    }

    @Override
    public List<Student> getAllStudents() {
        return Collections.unmodifiableList(students);
    }

    @Override
    public Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }

    @Override
    public boolean updateStudent(int studentId, String name, String programme,
                                 String supportCategory) {
        Student student = findStudentById(studentId);
        if (student == null) {
            return false;
        }
        student.setName(name);
        student.setProgramme(programme);
        student.setSupportCategory(supportCategory);
        return true;
    }

    @Override
    public boolean deleteStudent(int studentId) {
        Student student = findStudentById(studentId);
        return student != null && students.remove(student);
    }

    @Override
    public void saveStudentsToFile() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Student student : students) {
                writer.write(toFileLine(student));
                writer.newLine();
            }
        }
    }

    @Override
    public int loadStudentsFromFile() throws IOException {
        if (!Files.exists(filePath)) {
            throw new java.io.FileNotFoundException(
                    "The file '" + filePath + "' does not exist yet.");
        }

        List<Student> loadedStudents = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    Student student = fromFileLine(line);
                    if (containsId(loadedStudents, student.getStudentId())) {
                        throw new IOException("Duplicate student ID on line " + lineNumber + ".");
                    }
                    loadedStudents.add(student);
                } catch (IllegalArgumentException ex) {
                    throw new IOException("Invalid data on line " + lineNumber + ": "
                            + ex.getMessage(), ex);
                }
            }
        }

        students.clear();
        students.addAll(loadedStudents);
        return students.size();
    }

    public Path getFilePath() {
        return filePath.toAbsolutePath();
    }

    private String toFileLine(Student student) {
        return student.getStudentId() + DELIMITER
                + escape(student.getName()) + DELIMITER
                + escape(student.getProgramme()) + DELIMITER
                + escape(student.getSupportRecord().getCategory());
    }

    private Student fromFileLine(String line) {
        String[] fields = line.split("\\|", -1);
        if (fields.length != 4) {
            throw new IllegalArgumentException("Expected four fields separated by '|'.");
        }
        int id;
        try {
            id = Integer.parseInt(fields[0].trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Student ID is not numeric.", ex);
        }
        return new Student(id, unescape(fields[1]), unescape(fields[2]), unescape(fields[3]));
    }

    private boolean containsId(List<Student> list, int studentId) {
        for (Student student : list) {
            if (student.getStudentId() == studentId) {
                return true;
            }
        }
        return false;
    }

    private String escape(String value) {
        return value.replace("%", "%25").replace("|", "%7C");
    }

    private String unescape(String value) {
        return value.replace("%7C", "|").replace("%25", "%");
    }
}
