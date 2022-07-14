package spring.framework.books.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.framework.books.classes.TypesOfUsers;

public interface RoleRepository extends CrudRepository<TypesOfUsers,Long> {
}
