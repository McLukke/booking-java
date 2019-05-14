package ca.funfactory.booking.app.signon.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages = { "" })//TODO your repository package here
@EnableTransactionManagement
@PropertySource(value = "jpaConfig.properties", ignoreResourceNotFound = true)//TODO your jpaConfig properties, you can put that in the resource folder for now
public class JpaConfiguration {


	private static final String DATABASE_PLATFORM_PROPERTY_KEY = "databasePlatform";
	private static final String JTA_PLATFORM_PROPERTY_KEY = "jtaPlatform";
	private static final String SHOW_SQL_PROPERTY_KEY = "showSql";
	private static final String DATASOURCE_NAME_PROPERTY_KEY = "datasourceName";
	private static final String PACKAGES_TO_SCAN_PROPERTY_KEY = "packagesToScan";
	private static final String LAZY_LOAD_NO_TRAN_PROPERTY_KEY = "lazyLoadNoTran";
	@Autowired
	private Environment environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory
				.setPackagesToScan(new String[] { getEnvironment().getProperty(PACKAGES_TO_SCAN_PROPERTY_KEY) });
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactory.setJpaProperties(jpaProperties());
		return entityManagerFactory;
	}

	@Bean
	public DataSource dataSource() {
		String jndiName = getEnvironment().getProperty(DATASOURCE_NAME_PROPERTY_KEY);
		JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		return dsLookup.getDataSource(jndiName);
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabasePlatform(getEnvironment().getProperty(DATABASE_PLATFORM_PROPERTY_KEY));
		return jpaVendorAdapter;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public Properties jpaProperties() {
		Properties properties = new Properties();
		properties.setProperty(org.hibernate.cfg.Environment.JTA_PLATFORM,
				getEnvironment().getProperty(JTA_PLATFORM_PROPERTY_KEY));
		properties.setProperty(org.hibernate.cfg.Environment.DIALECT,
				getEnvironment().getProperty(DATABASE_PLATFORM_PROPERTY_KEY));
		properties.setProperty(org.hibernate.cfg.Environment.SHOW_SQL,
				getEnvironment().getProperty(SHOW_SQL_PROPERTY_KEY));
		properties.setProperty(org.hibernate.cfg.Environment.ENABLE_LAZY_LOAD_NO_TRANS,
				getEnvironment().getProperty(LAZY_LOAD_NO_TRAN_PROPERTY_KEY));
		properties.setProperty("hibernate.event.merge.entity_copy_observer", "allow");
		
		return properties;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
