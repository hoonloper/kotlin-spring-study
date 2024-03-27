
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ["io.study.multimodule.storage.db.main"])
@EnableJpaRepositories(basePackages = ["io.study.multimodule.storage.db.main"])
internal class MainJpaConfig