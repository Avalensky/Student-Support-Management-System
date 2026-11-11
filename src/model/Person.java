package model;
public abstract class Person {
/**
2
* Abstract parent class containing attributes

* shared by people in the system.
4
*/
    private int id;
    private String name;

    protected Person(int id, String name) {
        setId(id);
        setName(name);
    }

    public int getId() {
        return id;
    }

    public final void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "ID must be a positive number."
            );
        }

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Name may not be empty."
            );
        }

        this.name = name.trim();
    }

    public abstract String getDisplayDetails();
}