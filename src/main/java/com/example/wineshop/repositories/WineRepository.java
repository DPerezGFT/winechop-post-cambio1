package com.example.wineshop.repositories;

import com.example.wineshop.models.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {
    @Query(value = "SELECT * FROM wine w ORDER BY w.rating DESC LIMIT 10", nativeQuery = true)
    List<Wine> findTopRating();

    @Query(value = "SELECT * FROM wine w ORDER BY w.price DESC LIMIT 10", nativeQuery = true)
    List<Wine> findTopPrice();

    @Query(value = "SELECT * FROM wine w ORDER BY (w.rating/w.price) DESC LIMIT 10", nativeQuery = true)
    List<Wine> findTopRatio();

    @Query(value = "SELECT name, a.year as year, num_reviews, price, rating, acidity, body from wine a inner join (SELECT year FROM wine w GROUP BY w.year ORDER BY AVG(w.rating) DESC LIMIT 10) as b on a.year = b.year", nativeQuery = true)
    List<Wine> findTopYears();
}
