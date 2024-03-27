
@Configuration
internal class MainDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "multi-module.datasource.main")
    fun mainHikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    fun mainDataSource(@Qualifier("mainHikariConfig") config: HikariConfig): HikariDataSource {
        return HikariDataSource(config)
    }
}