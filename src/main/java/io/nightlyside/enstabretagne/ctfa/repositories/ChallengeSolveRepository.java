package io.nightlyside.enstabretagne.ctfa.repositories;

import io.nightlyside.enstabretagne.ctfa.entities.ChallengeSolve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ChallengeSolveRepository extends CrudRepository<ChallengeSolve, Integer> {
    ChallengeSolve findById(int id);

    List<ChallengeSolve> findAllByChallengeIdEquals(int challengeId);
    List<ChallengeSolve> findAllByUserIdEquals(int userId);
    List<ChallengeSolve> findAllByUserIdIn(Collection<Integer> userId);

    Integer countChallengeSolveByUserIdEquals(int userId);
    Integer countChallengeSolveByChallengeIdEquals(int challengeId);

    @Query("SELECT coalesce(max(challengeSolve.id), 0) FROM ChallengeSolve challengeSolve")
    public Integer getMaxId();
}
