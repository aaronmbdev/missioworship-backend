package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Voting;
import org.springframework.data.repository.CrudRepository;

public interface VotingRepository extends CrudRepository<Integer, Voting> {
}
