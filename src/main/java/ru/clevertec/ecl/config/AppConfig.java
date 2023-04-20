package ru.clevertec.ecl.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.clevertec.ecl")
@PropertySources({
        @PropertySource("classpath:dev-dev-db.properties"),
        @PropertySource("classpath:prod-dev-db.properties"),
})
@Slf4j
public class AppConfig implements WebApplicationInitializer {

    private Environment env;

    public AppConfig() {
    }

    @Autowired
    public AppConfig(ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    @Autowired
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Profile("dev")
    @Bean
    public DataSource securityDataSource() {
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
        try {
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
            securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
            securityDataSource.setUser(env.getProperty("jdbc.user"));
            securityDataSource.setPassword(env.getProperty("jdbc.password"));
            securityDataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("connection.pool.maxPoolSize")));
            securityDataSource.setMinPoolSize(Integer.parseInt(env.getProperty("connection.pool.minPoolSize")));
            securityDataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("connection.pool.maxIdleTime")));
            securityDataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("connection.pool.initialPoolSize")));
        } catch (PropertyVetoException e) {

            //TODO log "Error during loading database properties"
        }
        //TODO log "Database properties has been initialized"
        return securityDataSource;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", System.getenv("APP_ENV"));
    }
}
