package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    boolean existsByLinkToTrack(final String link);
    boolean existsByLinkToYoutube(final String link);

    @Query(value="SELECT * FROM songs s ORDER BY s.id limit ?1 , ?2", nativeQuery = true)
    public List<Song> findAllByPagination(int offset, int limit);
}
