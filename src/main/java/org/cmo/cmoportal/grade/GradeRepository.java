package org.cmo.cmoportal.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    Optional<Grade> findByExaminerIdAndSubmissionId(Integer examinerId, Integer submissionId);
    List<Grade> findBySubmissionId(Integer integer);
}
