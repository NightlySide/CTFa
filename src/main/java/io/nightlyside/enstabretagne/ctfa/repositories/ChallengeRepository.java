package io.nightlyside.enstabretagne.ctfa.repositories;

import io.nightlyside.enstabretagne.ctfa.entities.Challenge;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeRepository extends CrudRepository<Challenge, Integer> {
    public Challenge findById(int id);
    public List<Challenge> findAllByCategoryIdEquals(int categoryId);
}
