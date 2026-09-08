package main.java.edu.johnlab.radforduniversity.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.java.edu.johnlab.radforduniversity.model.Category;
import static main.java.edu.johnlab.radforduniversity.model.UserAuth.getConnectionDataBase;
import java.sql.SQLException;

public class CategoryRepository {

    public Category findByCategoryName(String categoryName) {
        Category cat = null;
        String sql = "select * from categories where category_name = ?";

        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, categoryName);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                cat = new Category();
                cat.setIdCategory(rs.getString("id_category"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar categoría: " + e.getMessage());
        }
        return cat;
    }

    public Category findByCategoryId(String idCategory) {
        Category cat = null;
        String sql = "select * from categories where id_category = ?";

        try (PreparedStatement pstm = getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, idCategory);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                cat = new Category();
                cat.setCategoryName(rs.getString("category_name"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar categoría: " + e.getMessage());
        }
        return cat;
    }
}
