package com.ShortURL.ShortURL.persistence.repository;

import com.ShortURL.ShortURL.persistence.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query(
            "SELECT u FROM Url u" +
                    " WHERE u.shortCode = :shortCode")
    Optional<Url> findByShortCode(String shortCode);
}
