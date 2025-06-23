package src.down;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.*;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.repository",  // 엔티티/리포지토리 위치
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DataSourceConfig {

    @Bean(name = "db1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource db1DataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource db2DataSource() {
        return new HikariDataSource();
    }

    @Primary
    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(
            @Qualifier("db1DataSource") DataSource db1,
            @Qualifier("db2DataSource") DataSource db2
    ) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("db1", db1);
        targetDataSources.put("db2", db2);

        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(db1); // 기본 DB
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("routingDataSource") DataSource dataSource,
            JpaProperties jpaProperties
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.domain") // 엔티티 위치
                .persistenceUnit("default")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}

