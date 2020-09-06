package lk.ijse.dep.pos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
public class HibernateConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds){
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        //set the data source
        lsfb.setDataSource(ds);
        // set entity package to identify the entities
        lsfb.setPackagesToScan("lk.ijse.dep.pos.entity");
        lsfb.setHibernateProperties();
        return lsfb;
    }
}

