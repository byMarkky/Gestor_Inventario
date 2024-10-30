package org.marco.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private static String URL_CONEX_BD;
    private static String USER;
    private static String PASS;

    private static ConnectionManager instance;
    private static HikariDataSource dataSource;

    private ConnectionManager(){
        assignProperties();
        configureDataSource(); // Configuración del pool de conexiones con HikariCP

    }
    public static ConnectionManager getInstance(){
        if(instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            logger.debug("Successful connection");
            return connection;
        } catch (SQLException e) {
            logger.error("There has been an error while creating the connection: " + e);
            throw e;
        }
    }

    public void beginTransaction(Connection conn) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Connection is not available");
        }
        conn.setAutoCommit(false);
    }

    public void commitTransaction(Connection conn) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Connection is not available");
        }
        conn.commit();
        conn.setAutoCommit(true);
    }

    public void rollbackTransaction(Connection conn) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Connection is not available");
        }
        conn.rollback();
        conn.setAutoCommit(true);  // Resetear el auto-commit antes de devolver al pool
    }



    // Cierra el pool de conexiones
    public void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("DataSource has been closed.");
        }
    }

    private void assignProperties(){
        Properties props = new Properties();

        try {
            props.load(Files.newInputStream(Paths.get("application.properties")));

            URL_CONEX_BD = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.pass");
            System.out.println("--- Loaded properties file ---");
            System.out.println("URL_CONEX_BD = " + URL_CONEX_BD);
            System.out.println("USER = " + USER);
            System.out.println("PASS = " + PASS);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Configura el pool de conexiones usando HikariCP
    private void configureDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL_CONEX_BD);
        config.setUsername(USER);
        config.setPassword(PASS);

        // Opciones adicionales de configuración del pool
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(600000); // 10 minutos de inactividad antes de cerrar
        config.setMaxLifetime(1800000); // 30 minutos de tiempo máximo de vida de las conexiones
        config.setConnectionTimeout(30000); // 30 segundos de timeout para obtener una conexión

        dataSource = new HikariDataSource(config);
    }


}
