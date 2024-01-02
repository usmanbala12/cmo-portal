package org.cmo.cmoportal.grade;

import lombok.RequiredArgsConstructor;
import org.cmo.cmoportal.cmoUser.MyUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public String addGrade(Grade grade, @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        gradeService.addGrade(grade);
        return "success";
    }
}
