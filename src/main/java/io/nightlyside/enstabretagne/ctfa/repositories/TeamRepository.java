package io.nightlyside.enstabretagne.ctfa.repositories;

import io.nightlyside.enstabretagne.ctfa.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import io.nightlyside.enstabretagne.ctfa.entities.Team;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    public Team findById(int id);
    public Team findByTeamname(String teamname);

    public List<Team> findByTeamnameLike(String recherche);

    @Query("SELECT coalesce(max(team.id), 0) FROM Team team")
    public Integer getMaxId();

    @Query("SELECT t FROM Team t WHERE LOWER(t.teamname) LIKE %?1%")
    public List<Team> search(String query);
}
