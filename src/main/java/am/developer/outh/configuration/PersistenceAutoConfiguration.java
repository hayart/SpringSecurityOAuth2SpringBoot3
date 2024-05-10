package am.developer.outh.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;

@Slf4j
@Configuration
public class PersistenceAutoConfiguration {
    protected Environment env;

    public PersistenceAutoConfiguration() {

    }

    protected void setHibernateProperties(LocalContainerEntityManagerFactoryBean em) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.hibernate.dialect"));

        String timeZone = env.getProperty("app.timeZone");
        if (timeZone == null || timeZone.isEmpty()) {
            timeZone = "Europe/Paris";
        }
        log.info("Hibernate time zone is {}", timeZone);
        properties.put("hibernate.jdbc.time_zone", timeZone);
        em.setJpaPropertyMap(properties);
    }

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }
}
