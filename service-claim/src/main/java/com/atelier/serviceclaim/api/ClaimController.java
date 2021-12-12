package com.atelier.serviceclaim.api;

import com.atelier.serviceclaim.api.dto.ClaimDto;
import com.atelier.serviceclaim.api.dto.UserDto;
import com.atelier.serviceclaim.repo.model.Claim;
import com.atelier.serviceclaim.repo.model.StatusName;
import com.atelier.serviceclaim.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/claims")
@RestController
public final class ClaimController {

    private final ClaimService claimService;

    @GetMapping
    public ResponseEntity<List<Claim>> showAll() {
        final List<Claim> claims = claimService.getAll();
        return ResponseEntity.ok(claims);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> showById(@PathVariable long id) {
        try {
            final Claim claim = claimService.getById(id);

            return ResponseEntity.ok(claim);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserDto> showUserById(@PathVariable long id) {
        try {
            final UserDto userDto = claimService.getUserById(id);

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ClaimDto claimDto) {
        String description = claimDto.description();
        StatusName status = StatusName.valueOf(claimDto.status());
        long userId = claimDto.userId();
        final long claimId = claimService.add(description, status, userId);
        final String claimUri = String.format("/claims/%d", claimId);

        return ResponseEntity.created(URI.create(claimUri)).build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody ClaimDto claimDto) {
        String description = claimDto.description();
        StatusName status = StatusName.valueOf(claimDto.status());
        long userId = claimDto.userId();

        try {
            claimService.update(id, description, status, userId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        claimService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
