package org.cmo.cmoportal.cmoUser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CmoUserRepository cmoUserRepository;

    public List<CmoUser> getUsers(UserType userType, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
       return cmoUserRepository.findByType(userType, pageable);
    }

    public Optional<CmoUser> getUser(Integer id) {
       return cmoUserRepository.findById(id);
    }

}
