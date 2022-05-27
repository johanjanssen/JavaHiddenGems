package com.examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class TestcontainersTest {

    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.28");

    @BeforeEach
    public void setUp() {
    }


    private static final String createBookTable = """
            CREATE TABLE BOOK
            (
            ID serial,
            NAME varchar(100) NOT NULL,
            PRIMARY KEY (ID)
            )""";

    private static final String storeBook = "INSERT INTO BOOK (NAME) VALUES (?)";

    private static final String retrieveBooks = "SELECT * FROM BOOK";


    @Test
    public void testSimplePutAndGet() throws SQLException {
        String url = mySQLContainer.getJdbcUrl();
        String username = mySQLContainer.getUsername();
        String password = mySQLContainer.getPassword();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Statement statement = conn.createStatement();
            statement.execute(createBookTable);

            PreparedStatement preparedStatement = conn.prepareStatement(storeBook);
            preparedStatement.setString(1, "Spring Boot in Action");
            preparedStatement.executeUpdate();
            preparedStatement.setString(1, "Learning MySQL");
            preparedStatement.executeUpdate();

            ResultSet rs = statement.executeQuery(retrieveBooks);
            rs.next();
            assertEquals("Spring Boot in Action", rs.getString("NAME"));
            rs.next();
            assertEquals("Learning MySQL", rs.getString("NAME"));

        }
    }
}
