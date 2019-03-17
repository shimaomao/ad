package com.planx.advertise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {
	
	List<UserToken> findByUserIdAndType(String userId, int type);
	
	Optional<UserToken> findByToken(String token);
	
}
