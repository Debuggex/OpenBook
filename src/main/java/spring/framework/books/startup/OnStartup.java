package spring.framework.books.startup;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;


@Slf4j
@Component
public class OnStartup implements ApplicationListener<ContextRefreshedEvent> {


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

         File file=new File("../../../Files-Upload");

        try {
            System.out.println("Deleting Directory!!");
            FileUtils.deleteDirectory(file);
            System.out.println("Directory Deleted!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
