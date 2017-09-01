package org.iplab.JDBC;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by js982 on 2017/8/31.
 */
public class DBinit {
    Properties properties = new Properties();

    public DBinit() throws IOException, SQLException {
        properties = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("G:\\WORK_FILE\\JAVA\\JavaSample\\src\\org\\iplab\\JDBC\\database.properties"))){
            properties.load(in);
        }
        String drivers = properties.getProperty("jdbc.drivers");
        if(drivers != null)
            System.setProperty("jdbc.drivers",drivers);

        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        Connection conn = DriverManager.getConnection(url, username, password);

        String sql1 = "insert into book_table(name,author,isbn) values('java core', 'jiahsuo', '982986555')";
        String sql2 = "insert into book_table(name,author,isbn) values('sql core', 'jiahsuo', '435872345')";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql1);
        statement.executeUpdate(sql2);
    }

    public static void main(String[] args) throws IOException, SQLException {
        DBinit dBinit = new DBinit();
    }
}
