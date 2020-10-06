package io.nightlyside.enstabretagne.ctfa.repositories;

import org.springframework.data.repository.CrudRepository;
import io.nightlyside.enstabretagne.ctfa.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
