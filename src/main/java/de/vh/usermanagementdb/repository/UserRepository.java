package de.vh.usermanagementdb.repository;

import de.vh.usermanagementdb.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{


}
