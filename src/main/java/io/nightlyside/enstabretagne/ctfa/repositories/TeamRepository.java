package io.nightlyside.enstabretagne.ctfa.repositories;

import org.springframework.data.repository.CrudRepository;
import io.nightlyside.enstabretagne.ctfa.entities.Team;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    public Team findById(int id);
    public Team findByTeamname(String teamname);

    public List<Team> findByTeamnameLike(String recherche);
}
