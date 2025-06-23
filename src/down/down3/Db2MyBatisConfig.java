@Configuration
@MapperScan(basePackages = "com.example.db2.mapper", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class Db2MyBatisConfig {

    @Bean(name = "db2DataSource")
    @ConfigurationProperties("spring.datasource.db2")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db2SqlSessionFactory")
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        // ▶️ XML Mapper 경로 설정
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/db2/**/*.xml");
        factory.setMapperLocations(resources);

        return factory.getObject();
    }

    @Bean(name = "db2SqlSessionTemplate")
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
