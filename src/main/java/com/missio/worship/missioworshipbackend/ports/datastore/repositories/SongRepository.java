package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    boolean existsByLinkToYoutube(final String link);

    @Query(value="SELECT * FROM songs ORDER BY ?1 DESC LIMIT ?3, ?2 ", nativeQuery = true)
    List<Song> findAllByPagination(String orderClause, int limit, int offset);

    @Query(value="SELECT COUNT(*) as total FROM songs ORDER BY ?1 DESC ", nativeQuery = true)
    Long findAllByPaginationCount(String orderClause);

    @Query(value="SELECT * FROM songs WHERE active = ?4 ORDER BY ?1 DESC LIMIT ?3, ?2 ", nativeQuery = true)
    List<Song> findAllByPaginationWithActive(String orderClause, int limit, int offset, boolean active);

    @Query(value="SELECT COUNT(*) as total FROM songs WHERE active = ?2 ORDER BY ?1 DESC ", nativeQuery = true)
    Long findAllByPaginationWithActiveCount(String orderClause, boolean active);
}
