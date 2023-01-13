package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User getUser(final Integer id) throws UserNotFound {
        return repository.findById(id).orElseThrow(() -> new UserNotFound("El usuario con id '" + id + "' no existe"));
    }
}
