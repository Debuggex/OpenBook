package spring.framework.books.startup;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import spring.framework.books.classes.TypesOfUsers;
import spring.framework.books.repositories.RoleRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


@Slf4j
@Component
@PropertySource("classpath:config.properties")
public class OnStartup implements ApplicationListener<ContextRefreshedEvent>, EnvironmentAware {

    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        OnStartup.environment = environment;
    }


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (Objects.requireNonNull(environment.getProperty("IS_TESTING_ENABLED")).equalsIgnoreCase("1")){
            File file=new File("Files-Upload");

            try {
                System.out.println("Deleting Directory!!");
                FileUtils.deleteDirectory(file);
                System.out.println("Directory Deleted!!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
