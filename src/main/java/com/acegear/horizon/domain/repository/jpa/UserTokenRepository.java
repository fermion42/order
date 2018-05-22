package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.constraint.LoginPlatform;
import com.acegear.horizon.domain.models.jpa.UserToken;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends MongoRepository<UserToken, String> {

    List<UserToken> findByUserId(Long userId);

    UserToken findTopByUserId(Long userId);

    Optional<UserToken> findByUserIdAndPlatform(Long userId, LoginPlatform platform);
}
