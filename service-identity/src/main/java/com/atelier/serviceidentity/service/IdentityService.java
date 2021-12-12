package com.atelier.serviceidentity.service;

import com.atelier.serviceidentity.api.dto.UserDto;
import com.atelier.serviceidentity.repo.UserRepo;
import com.atelier.serviceidentity.repo.model.User;
import com.atelier.serviceidentity.repo.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class IdentityService {

    private final UserRepo userRepo;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(long id) {
        final Optional<User> user = userRepo.findById(id);

        if (user.isPresent())
            return user.get();
        else
            throw new IllegalArgumentException("Invalid user ID");
    }

    public UserDto getDtoById(long id) {
        final Optional<User> user = userRepo.findById(id);

        if (user.isPresent()) {
            return new UserDto(user.get().getName(), user.get().getPhone(), user.get().getEmail(),
                    user.get().getPassword(), user.get().getUserType().name());
        } else
            throw new IllegalArgumentException("Invalid user ID");
    }

    public long add(String name, String phone, String email, String password, UserType userType) {
        if (userType == null) userType = UserType.CLIENT;
        final User user = new User(name, phone, email, password, userType);
        Optional<User> maybeUser = userRepo.findByEmail(email);
        if (maybeUser.isPresent())
            throw new IllegalArgumentException("This user already exists!");
        else {
            final User newUser = userRepo.save(user);
            return newUser.getId();
        }
    }

    public void update(long id, String name, String phone, String email, String password, UserType userType) {
        Optional<User> oldUser = userRepo.findById(id);

        if (oldUser.isEmpty())
            throw new IllegalArgumentException("Invalid user ID");

        final User user = oldUser.get();
        if (name != null && !name.isBlank()) user.setName(name);
        if (phone != null && !phone.isBlank()) user.setPhone(phone);
        if (email != null && !email.isBlank()) user.setEmail(email);
        if (password != null && !password.isBlank()) user.setPassword(password);
        if (userType != null) user.setUserType(userType);
        else user.setUserType(UserType.CLIENT);
        userRepo.save(user);

    }

    public void delete(long id) {
        userRepo.deleteById(id);
    }

}
