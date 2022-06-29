package spring.framework.books.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.framework.books.classes.Book;

public interface bookRespository extends CrudRepository<Book,Long> {
}
