package org.cmo.cmoportal.cmoUser;

import com.google.api.services.drive.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CmoUserRepository extends JpaRepository<CmoUser, Integer> {
    Optional<CmoUser> findByEmail(String email);
    List<CmoUser> findByType(UserType userType, Pageable pageable);

    List<CmoUser> findByType(UserType userType);
}
