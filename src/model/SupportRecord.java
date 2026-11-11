package model;

public class SupportRecord {

    private String category;

    public SupportRecord(String category) {
        setCategory(category);
    }

    public String getCategory() {
        return category;
    }

    public final void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Support category may not be empty."
            );
        }

        this.category = category.trim();
    }

    @Override
    public String toString() {
        return category;
    }
}