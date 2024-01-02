package org.cmo.cmoportal.submission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cmo.cmoportal.cmoUser.CmoUser;
import org.cmo.cmoportal.cmoUser.CmoUserRepository;
import org.cmo.cmoportal.cmoUser.UserType;
import org.cmo.cmoportal.grade.Grade;
import org.cmo.cmoportal.grade.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionService {

    private final GradeRepository gradeRepository;

    private final CmoUserRepository cmoUserRepository;
    private final SubmissionRepository submissionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void addSubmission(Submission submission) {
        CmoUser merged = entityManager.merge(submission.getOwner());
        submission.setOwner(merged);
        submissionRepository.save(submission);
    }

    public void addGrade(CmoUser examiner, Submission submission, Integer grade) {
        Grade obj = new Grade();
        obj.setGrade(grade);
        obj.setExaminer(examiner);
        obj.setSubmission(submission);

        gradeRepository.save(obj);
    }

    public List<Grade> findGrades(Submission submission) {
        return gradeRepository.findBySubmissionId(submission.getId());
    }

    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    public List<Submission> findAssigned(Integer examinerId) {
        return submissionRepository.findAllByExaminersId(examinerId);
    }

    public List<Submission> findAllByOwner(Integer in) {
        return submissionRepository.findByOwnerId(in);
    }

    public Optional<Submission> findById(Integer submissionId) {
        return submissionRepository.findById(submissionId);
    }

    public Optional<Grade> findGradeBySubmissionAndExaminerId(Integer submissionId, Integer examinerId) {
        return gradeRepository.findByExaminerIdAndSubmissionId(examinerId, submissionId);
    }

    public CmoUser findExaminerWithLowestSubmission() {
        List<CmoUser> examiners = cmoUserRepository.findByType(UserType.EXAMINER);
        long max = 0;
        CmoUser lowestSubmission = new CmoUser();
        for (CmoUser cmouser: examiners) {
           long submissionCount = submissionRepository.findAllByExaminersId(cmouser.getId()).stream().count();
           if (submissionCount <= max) {
               max = submissionCount;
               lowestSubmission = cmouser;
           }
        }
        return lowestSubmission;
    }

}
