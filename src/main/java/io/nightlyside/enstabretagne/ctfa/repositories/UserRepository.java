package io.nightlyside.enstabretagne.ctfa.repositories;

import io.nightlyside.enstabretagne.ctfa.entities.Challenge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    public User findById(int id);
    public User findByUsername(String username);
    public User findByEmail(String email);

    public List<User> findByUsernameLike(String recherche);

    public List<User> findByTeamIdEquals(int teamId);

    @Query("SELECT coalesce(max(user.id), 0) FROM User user")
    public Integer getMaxId();

    @Query("SELECT u FROM User u JOIN Team t ON u.teamId = t.id WHERE LOWER(CONCAT(u.username, ' ', t.teamname)) LIKE %?1%")
    public List<User> search(String query);
}
