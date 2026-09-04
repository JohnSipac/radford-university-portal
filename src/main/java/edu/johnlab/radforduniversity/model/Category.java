package main.java.edu.johnlab.radforduniversity.model;

public class Category {
    
    private String idCategory;
    private String categoryName;
    private String description;

    public Category(String idCategory, String categoryName, String description) {
        this.idCategory = idCategory;
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
