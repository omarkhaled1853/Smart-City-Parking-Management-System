package jdbcDemo.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insertUser(User user){
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        int rowAffected = jdbcTemplate.update(sql, user.getName(), user.getEmail());

        return rowAffected > 0;
    }
}
