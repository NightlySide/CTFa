package io.nightlyside.enstabretagne.ctfa.repositories;

import io.nightlyside.enstabretagne.ctfa.entities.Challenge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeRepository extends CrudRepository<Challenge, Integer> {
    public Challenge findById(int id);
    public Challenge findByTitleLike(String title);
    public List<Challenge> findAllByCategoryIdEquals(int categoryId);

    @Query("SELECT coalesce(max(chall.id), 0) FROM Challenge chall")
    public Integer getMaxId();

    @Query("SELECT c FROM Challenge c WHERE LOWER(CONCAT(c.title, ' ', c.description, ' ', c.score)) LIKE %?1%")
    public List<Challenge> search(String query);
}
