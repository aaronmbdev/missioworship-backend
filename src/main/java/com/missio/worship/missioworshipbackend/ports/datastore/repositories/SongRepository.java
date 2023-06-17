package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    boolean existsByLinkToYoutube(final String link);

    @Query(value="SELECT * FROM songs s WHERE s.name LIKE ?3 ORDER BY s.name DESC LIMIT ?2, ?1 ", nativeQuery = true)
    List<Song> findAllByPagination(int limit, int offset, String search);

    @Query(value="SELECT COUNT(*) FROM songs s WHERE s.name LIKE ?1", nativeQuery = true)
    Long countAllByName(String search);

    @Query(value="SELECT * FROM songs s WHERE s.active = ?3 AND s.name LIKE ?4 ORDER BY s.name DESC LIMIT ?2, ?1", nativeQuery = true)
    List<Song> findAllByPaginationWithActive(int limit, int offset, boolean active, String search);

    @Query(value="SELECT COUNT(*) FROM songs s WHERE s.active = ?1 AND s.name LIKE ?2", nativeQuery = true)
    Long countAllByActiveEq(boolean active, String search);

}
