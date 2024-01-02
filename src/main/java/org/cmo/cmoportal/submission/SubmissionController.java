package org.cmo.cmoportal.submission;

import com.google.api.client.http.FileContent;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.User;
import lombok.RequiredArgsConstructor;
import org.cmo.cmoportal.cmoUser.CmoUser;
import org.cmo.cmoportal.cmoUser.MyUserPrincipal;
import org.cmo.cmoportal.cmoUser.UserType;
import org.cmo.cmoportal.grade.Grade;
import org.cmo.cmoportal.state.State;
import org.springframework.boot.Banner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionController {

    private final Drive drive;
    private final SubmissionService service;
    private final State state;
    @GetMapping
    public String getSubmissionForm() {
        return "submitFile.html";
    }

    @PostMapping
    public String processSubmission(@RequestParam(value = "pdffile", required = false) MultipartFile file, @AuthenticationPrincipal MyUserPrincipal userDetails) throws IOException {
        System.out.println(file.toString());
        java.io.File saved = multipartToFile(file, "testPdf");
        File toUpload = new File();
        toUpload.setName("file"+LocalDateTime.now().toString());
        toUpload.setMimeType("application/pdf");
        FileContent mediaContent = new FileContent("application/pdf", saved);
        try {
            File uploaded = drive.files().create(toUpload, mediaContent)
                    .setFields("id")
                    .execute();

            Submission submission = new Submission();
            submission.setSubmissionTime(LocalDateTime.now());
            submission.setFilename(file.getOriginalFilename());
            submission.setFileDriveId(uploaded.getId());
            submission.setOwner(userDetails.getCmoUser());

            if (state.getAutoAssign()) {
                while (submission.getExaminers().stream().count() < 3) {
                    CmoUser examiner = service.findExaminerWithLowestSubmission();
                    if (submission.getExaminers().contains(examiner)) break;
                    submission.addExaminer(examiner);
                }
            }

            service.addSubmission(submission);

            User u1 = new User();
            u1.setEmailAddress("usmanxp12@gmail.com");
            List<User> users = new ArrayList();
            users.add(u1);
            uploaded.setOwners(users);
            System.out.println("File ID: " + uploaded.getId());
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }

        return "success";
    }

    @GetMapping(value = "/file/{id}", produces = MediaType.APPLICATION_PDF_VALUE)

    public @ResponseBody Resource returnFile(@PathVariable String id) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        drive.files().get(id).executeMediaAndDownloadTo(outputStream);

        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        System.out.println(outputStream.toByteArray());
        return byteArrayResource;
    }


    /*
    * This controller method will fetch all submissions available in the system. meant for admin acesss.
    * */

    @GetMapping("/all")
    public String getAllSubmissions(Model model) {
        List<Submission> submissions = service.findAll();
        model.addAttribute("submissions", submissions);
        return "submissions";
    }

    /*
    * This controller method will fetch all submissions assigned to an examiner user type
    * */
    @GetMapping("/assigned")
    public String getAssigned(@AuthenticationPrincipal MyUserPrincipal userDetails, Model model) {
        List<Submission> submissions = service.findAssigned(userDetails.getCmoUser().getId());
        model.addAttribute("submissions", submissions);
        return "assigned";
    }

    @ModelAttribute(name = "grade")
    public Grade grade() {
        return new Grade();
    }

    @GetMapping("/{id}")
    public String getSubmissionDetail(@PathVariable Integer id, Model model, @AuthenticationPrincipal MyUserPrincipal userDetails) {
        Optional<Submission> byId = service.findById(id);
        if(byId.isPresent()) {
            if(userDetails.getCmoUser().getType().equals(UserType.EXAMINER)) {
                Optional<Grade> grade = service.findGradeBySubmissionAndExaminerId(id, userDetails.getCmoUser().getId());
                if(grade.isPresent()) {
                    model.addAttribute("graded", true);
                } else {
                    model.addAttribute("graded", false);
                }
            }
            List<Grade> grades = service.findGrades(byId.get());
            Integer total = grades.stream().reduce(0, (subtotal, element) -> subtotal + element.getGrade(), Integer::sum);
            model.addAttribute("submission", byId.get());
            model.addAttribute("grades", grades);
            model.addAttribute("totalGrade", total);
            return "submissionDetail";
        } else {
            return "notFound";
        }
    }

    public java.io.File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        java.io.File convFile = new java.io.File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}
