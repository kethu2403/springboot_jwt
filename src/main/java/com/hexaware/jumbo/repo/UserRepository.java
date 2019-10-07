package com.hexaware.jumbo.repo;

import com.hexaware.jumbo.model.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * UserRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
    /**
     * @param userName username.
     * @return user.
     */
    UserDetails findByUsername(String userName);

}
