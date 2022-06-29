package spring.framework.books.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.framework.books.classes.BookVolume;

public interface BookVolumeRespository extends CrudRepository<BookVolume, Long> {
}
