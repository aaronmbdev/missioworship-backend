package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
