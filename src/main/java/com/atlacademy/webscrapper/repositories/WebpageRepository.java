package com.atlacademy.webscrapper.repositories;

import com.atlacademy.webscrapper.models.Webpage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebpageRepository extends CrudRepository<Webpage, Integer> {

    @Query("SELECT w FROM Webpage w WHERE domain LIKE %:text% OR description LIKE %:text% " +
            "OR title LIKE %:text% OR url LIKE %:text% ORDER BY rank DESC")
    List<Webpage> findByText(@Param("text") String text);

    Webpage findByUrl(String url);
}
