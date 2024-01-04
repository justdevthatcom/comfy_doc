package ru.gmdev.comfy_doc.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

// deactivate
public class DataSourceConfiguration {

  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUsername("postgres");
    dataSource.setPassword("postgres");
    dataSource.setUrl(
            "jdbc:mysql://localhost:3306/myDb?createDatabaseIfNotExist=true");

    return dataSource;
  }
}
