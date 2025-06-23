@Configuration
@MapperScan(basePackages = "com.example.db1.mapper", sqlSessionFactoryRef = "db1SqlSessionFactory")
public class Db1MyBatisConfig {

    @Bean(name = "db1DataSource")
    @ConfigurationProperties("spring.datasource.db1")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db1SqlSessionFactory")
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        // ▶️ XML Mapper 경로 설정
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/db1/**/*.xml");
        factory.setMapperLocations(resources);

        return factory.getObject();
    }

    @Bean(name = "db1SqlSessionTemplate")
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
