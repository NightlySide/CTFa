package io.nightlyside.enstabretagne.ctfa.repositories;

import io.nightlyside.enstabretagne.ctfa.entities.ChallengeCategory;
import org.springframework.data.repository.CrudRepository;

public interface ChallengeCategoryRepository extends CrudRepository<ChallengeCategory, Integer> {
    public ChallengeCategory findById(int id);
}
