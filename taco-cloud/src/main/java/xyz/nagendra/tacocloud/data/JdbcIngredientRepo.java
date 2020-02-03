package xyz.nagendra.tacocloud.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.nagendra.tacocloud.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepo implements IngredientRepo {

    private static final String QUERY_FIND_ALL = "select id, name, type from ingredient";
    private static final String QUERY_FIND_BY_ID = "select id, name, type from ingredient where id=?";
    private static final String QUERY_SAVE = "insert into ingredient(id, name, type) values(?, ?, ?)";

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query(QUERY_FIND_ALL, this::toIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject(QUERY_FIND_ALL, this::toIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(QUERY_SAVE, ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient toIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
