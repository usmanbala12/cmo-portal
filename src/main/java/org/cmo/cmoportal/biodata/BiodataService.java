package org.cmo.cmoportal.biodata;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cmo.cmoportal.cmoUser.CmoUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BiodataService {

    private final BiodataRepository biodataRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Biodata> findByUserId(Integer userId) {
        return biodataRepository.findByUserId(userId);
    }

    public void addBiodata(Biodata biodata) {
        CmoUser merged = entityManager.merge(biodata.getUser());
        biodata.setUser(merged);
        biodataRepository.save(biodata);
    }

    public Optional<Biodata> getBiodata(Integer userid) {
        return biodataRepository.findByUserId(userid);
    }

}
