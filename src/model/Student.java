package model;
public class Student extends Person {
    private String programme;
    private SupportRecord supportRecord;

    public Student(int studentId, String name, String programme, String supportCategory) {
        super(studentId, name);
        setProgramme(programme);
        this.supportRecord = new SupportRecord(supportCategory);
    }
    public Student(int studentId, String name, String supportCategory) {
        this(studentId, name, "Not specified", supportCategory);
    }

    public int getStudentId() {
        return getId();
    }

    public String getProgramme() {
        return programme;
    }

    public final void setProgramme(String programme) {
        if (programme == null || programme.trim().isEmpty()) {
            throw new IllegalArgumentException("Programme may not be empty.");
        }
        this.programme = programme.trim();
    }

    public SupportRecord getSupportRecord() {
        return supportRecord;
    }

    public void setSupportCategory(String category) {
        supportRecord.setCategory(category);
    }
    @Override
    public String getDisplayDetails() {
        return String.format("ID: %d | Name: %s | Programme: %s | Support: %s",
                getStudentId(), getName(), programme, supportRecord.getCategory());
    }
    @Override
    public String toString() {
        return getDisplayDetails();
    }
}
