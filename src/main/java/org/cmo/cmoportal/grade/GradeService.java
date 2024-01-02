package org.cmo.cmoportal.grade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public void addGrade(Grade grade) {
        gradeRepository.save(grade);
    }

}
