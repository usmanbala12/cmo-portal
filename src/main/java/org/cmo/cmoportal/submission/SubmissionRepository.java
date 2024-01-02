package org.cmo.cmoportal.submission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByExaminersId(Integer integer);
    List<Submission> findByOwnerId(Integer integer);
}
