package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.jpa.ClubBase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by guoweike on 17/7/7.
 */
public interface ClubBaseRepository extends PagingAndSortingRepository<ClubBase, Long>, JpaSpecificationExecutor<ClubBase> {
    Optional<ClubBase> findByClubId(Long clubId);

}
