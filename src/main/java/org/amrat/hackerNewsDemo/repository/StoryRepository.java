package org.amrat.hackerNewsDemo.repository;

import org.amrat.hackerNewsDemo.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *  Repository class for Story
 */
@Repository
public interface StoryRepository extends JpaRepository<Story,Long> {

    @Query("select s from Story s where s.id=?1")
    Story findById(long id);
}
