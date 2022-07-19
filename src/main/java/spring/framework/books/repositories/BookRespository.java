package spring.framework.books.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.framework.books.classes.Book;

public interface BookRespository extends CrudRepository<Book,Long> {
}
