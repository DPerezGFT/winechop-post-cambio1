package com.example.wineshop.repositories;

import com.example.wineshop.models.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {
    @Query(value = "SELECT * FROM wine w ORDER BY w.rating DESC LIMIT 10", nativeQuery = true)
    List<Wine> findTopRating();
}
