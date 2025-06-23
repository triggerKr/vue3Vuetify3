package src.down.down4;
// 이건 아님...
@Configuration
@MapperScan(basePackages = "com.example.mapper.db2", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class Db2DataSourceConfig {

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db2SqlSessionFactory")
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(ds);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db2/**/*.xml"));
        return factory.getObject();
    }

    @Bean(name = "db2SqlSessionTemplate")
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sf) {
        return new SqlSessionTemplate(sf);
    }
}

