package spring.framework.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.books.classes.Author;

public interface authorRespository extends JpaRepository<Author,Long> {

}
