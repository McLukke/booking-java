package ca.funfactory.booking.app.signon.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans(value = {
		@ComponentScan("ca.funfactory.booking.app.signon.config.dao"),
		@ComponentScan("ca.funfactory.booking.app.signon.config.service")
})
public class AppConfig {
	public LocalSessionFactoryBean getSession
}
