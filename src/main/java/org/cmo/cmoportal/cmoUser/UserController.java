package org.cmo.cmoportal.cmoUser;

import lombok.RequiredArgsConstructor;
import org.cmo.cmoportal.biodata.Biodata;
import org.cmo.cmoportal.biodata.BiodataRepository;
import org.cmo.cmoportal.biodata.BiodataService;
import org.cmo.cmoportal.submission.Submission;
import org.cmo.cmoportal.submission.SubmissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StringToUserTypeConverter stringToUserTypeConverter;
    private final BiodataService biodataService;
    private final SubmissionService submissionService;

    private final PasswordEncoder passwordEncoder;
    @GetMapping("/{type}/all")
    public String getUsers(
            Model model,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @PathVariable String type
            ) {

        UserType userType = stringToUserTypeConverter.convert(type);
        List<CmoUser> users = userService.getUsers(userType, page, size);
        model.addAttribute("users", users);
        return "users";

    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Integer id, Model model) {
        Optional<CmoUser> user = userService.getUser(id);
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
            if (user.get().getType().equals(UserType.CANDIDATE)) {
                Optional<Biodata> biodata = biodataService.findByUserId(id);
                List<Submission> submissions = submissionService.findAllByOwner(id);
                model.addAttribute("biodata", biodata.orElse(null));
                model.addAttribute("submissions", submissions);
            }
            if (user.get().getType().equals(UserType.EXAMINER)) {
                List<Submission> submissions = submissionService.findAssigned(id);
                if(submissions.size() < 3) {
                    List<Submission> allSubs = submissionService.findAll();
                    System.out.println(allSubs.toString());
                    allSubs = allSubs.stream().filter(submissions::contains).filter(item -> item.getExaminers().size() > 2).toList();
                    model.addAttribute("toAssign", allSubs);
                }
                model.addAttribute("assigned", submissions);
            }
            return "user";
        } else {
            return "notFound";
        }
    }

    @GetMapping("/me")
    public String profile(@AuthenticationPrincipal MyUserPrincipal userPrincipal, Model model) {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        model.addAttribute("user", userPrincipal.getCmoUser());
        model.addAttribute("resetPassword", resetPasswordDto);
        return "profile";
    }

    @PostMapping("/resetPassword")
    public String passwordReset(@AuthenticationPrincipal MyUserPrincipal userPrincipal, ResetPasswordDto resetPasswordDto, Model model) {
        CmoUser cmoUser = userPrincipal.getCmoUser();
        // confirm old password is equal is current password
        boolean passwordCorrect = cmoUser.getPassword().equals(passwordEncoder.encode(resetPasswordDto.getOldPassword()));
        // confirm new password is equal to confirmation password
        boolean newPasswordConfirmed = resetPasswordDto.getNewPassword().equals(resetPasswordDto.getNewPassword1());

        if (passwordCorrect && newPasswordConfirmed) {
            cmoUser.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            model.addAttribute("passwordChanged", true);
        } else {
            model.addAttribute("correctPassword", passwordCorrect);
            model.addAttribute("passwordConfirmed", newPasswordConfirmed);
            return "profile";
        }
        return "profile";
    }

    @GetMapping("/inviteExaminer")
    public String inviteExaminer(Model model) {
        model.addAttribute("inviteExaminerDto", new InviteExaminerDto());
        return "inviteExaminer";
    }

    @PostMapping("/inviteExaminer")
    public String createExaminer(InviteExaminerDto inviteExaminerDto) {
        System.out.println(inviteExaminerDto.toString());
        return null;
    }

}
