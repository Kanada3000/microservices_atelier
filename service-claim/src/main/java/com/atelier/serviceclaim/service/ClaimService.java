package com.atelier.serviceclaim.service;

import com.atelier.serviceclaim.api.dto.UserDto;
import com.atelier.serviceclaim.repo.ClaimRepo;
import com.atelier.serviceclaim.repo.model.Claim;
import com.atelier.serviceclaim.repo.model.StatusName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ClaimService {

    private final String usersUrlAdress = "http://service-identity:8082/users";
    private final ClaimRepo claimRepo;

    public List<Claim> getAll() {
        return claimRepo.findAll();
    }

    public Claim getById(long id) {
        final Optional<Claim> claim = claimRepo.findById(id);

        if (claim.isPresent())
            return claim.get();
        else
            throw new IllegalArgumentException("Invalid claim ID");
    }

    public UserDto getUserById(long id) {
        final Optional<Claim> claim = claimRepo.findById(id);

        if (claim.isPresent()) {
            long userId = claim.get().getUserId();
            final RestTemplate restTemplate = new RestTemplate();
            final HttpEntity<Long> userRequest = new HttpEntity<>(userId);

            final ResponseEntity<UserDto> userResponse = restTemplate
                    .exchange(usersUrlAdress + "/" + userId + "/get",
                            HttpMethod.GET, userRequest, UserDto.class);

            if (userResponse.getStatusCode() != HttpStatus.NOT_FOUND)
                return userResponse.getBody();
            else
                throw new IllegalArgumentException("User not found!");
        } else
            throw new IllegalArgumentException("Invalid claim ID");
    }

    public long add(String description, StatusName status, long userId) {
        final Claim claim = new Claim(description, status, userId);
        final Claim newClaim = claimRepo.save(claim);

        return newClaim.getId();
    }

    public void update(long id, String description, StatusName status, long userId) {
        Optional<Claim> oldClaim = claimRepo.findById(id);

        if (oldClaim.isEmpty())
            throw new IllegalArgumentException("Invalid claim ID");

        final Claim claim = oldClaim.get();
        if (description != null && !description.isBlank()) claim.setDescription(description);
        if (status != null) claim.setStatus(status);
        if (userId != 0) claim.setUserId(userId);
        claimRepo.save(claim);

    }

    public void delete(long id) {
        claimRepo.deleteById(id);
    }
}
