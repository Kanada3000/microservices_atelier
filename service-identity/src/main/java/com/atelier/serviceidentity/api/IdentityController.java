package com.atelier.serviceidentity.api;

import com.atelier.serviceidentity.api.dto.UserDto;
import com.atelier.serviceidentity.repo.model.User;
import com.atelier.serviceidentity.repo.model.UserType;
import com.atelier.serviceidentity.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public final class IdentityController {

    private final IdentityService identityService;

    @GetMapping
    public ResponseEntity<List<User>> showAll() {
        final List<User> users = identityService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> showById(@PathVariable long id) {
        try {
            final User user = identityService.getById(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<UserDto> showDtoById(@PathVariable long id) {
        try {
            final UserDto userDto = identityService.getDtoById(id);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDto userDto) {
        String name = userDto.name();
        String phone = userDto.phone();
        String password = userDto.password();
        String email = userDto.email();
        UserType userType = UserType.valueOf(userDto.userType());
        final long userId = identityService.add(name, phone, email, password, userType);
        final String userUri = String.format("/users/%d", userId);

        return ResponseEntity.created(URI.create(userUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody UserDto userDto) {
        String name = userDto.name();
        String phone = userDto.phone();
        String password = userDto.password();
        String email = userDto.email();
        UserType userType = UserType.valueOf(userDto.userType());

        try {
            identityService.update(id, name, phone, email, password, userType);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        identityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
