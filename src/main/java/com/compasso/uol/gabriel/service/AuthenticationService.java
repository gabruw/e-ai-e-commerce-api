package com.compasso.uol.gabriel.service;

import java.util.Optional;

import com.compasso.uol.gabriel.entity.Authentication;

public interface AuthenticationService {

	Optional<Authentication> findById(Long id);

	Optional<Authentication> findByEmail(String email);

	Authentication persist(Authentication authentication);

	void deleteById(Long id);
}
