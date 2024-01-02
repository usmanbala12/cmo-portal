package org.cmo.cmoportal.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.User;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cmo.cmoportal.cmoUser.CmoUser;
import org.cmo.cmoportal.cmoUser.CmoUserRepository;
import org.cmo.cmoportal.cmoUser.UserType;
import org.cmo.cmoportal.grade.Grade;
import org.cmo.cmoportal.grade.GradeRepository;
import org.cmo.cmoportal.submission.Submission;
import org.cmo.cmoportal.submission.SubmissionCategory;
import org.cmo.cmoportal.submission.SubmissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
@Transactional
public class TestFileUpload implements CommandLineRunner {

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final PasswordEncoder passwordEncoder;
    private final CmoUserRepository cmoUserRepository;
    private final SubmissionRepository submissionRepository;

    private final GradeRepository gradeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String KEY_FILE_LOCATION = "/home/codegeek/Downloads/cmoportal-c3232d99d73c.json";

    private final Gmail service;
    @Override
    public void run(String... args) throws Exception {
//        final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, httpRequestInitializer)
//                .setApplicationName("cmoportal")
//                .build();

//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        GoogleCredential credential = GoogleCredential
//                .fromStream(new FileInputStream(KEY_FILE_LOCATION))
//                .createScoped(DriveScopes.all());
//
//        // Construct the drive service object.
//        Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
//                .setApplicationName("cmoportal").build();
//
//
//        FileList result = service.files().list()
//                .setPageSize(10)
//                .setFields("nextPageToken, files(id, name)")
//                .execute();
//
//        List<File> files = result.getFiles();
//        if (files == null || files.isEmpty()) {
//            System.out.println("No files found.");
//        } else {
//            System.out.println("Files:");
//            for (File file : files) {
//                System.out.printf("%s (%s) (%s)\n", file.getName(), file.getId(), file.getWebViewLink());
//                User u1 = new User();
//                u1.setEmailAddress("usmanxp12@gmail.com");
//                file.setShared(true);
//                file.setSharingUser(u1);
//            }
//        }

        CmoUser candidate = new CmoUser();
        candidate.setEmail("candidtae@cmo.com");
        candidate.setPassword(passwordEncoder.encode("testuser"));
        candidate.setFirstName("Candidate");
        candidate.setLastName("User");
        candidate.setRoles("user");
        candidate.setEnabled(true);
        candidate.setType(UserType.CANDIDATE);

        CmoUser examiner = new CmoUser();
        examiner.setEmail("examiner@cmo.com");
        examiner.setPassword(passwordEncoder.encode("testuser"));
        examiner.setFirstName("Examiner");
        examiner.setLastName("User");
        examiner.setRoles("examiner");
        examiner.setEnabled(true);
        examiner.setType(UserType.EXAMINER);

        CmoUser admin = new CmoUser();
        admin.setEmail("admin@cmo.com");
        admin.setPassword(passwordEncoder.encode("testuser"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRoles("admin");
        admin.setEnabled(true);
        admin.setType(UserType.STAFF);

        cmoUserRepository.saveAll(List.of(candidate, examiner, admin));

        Submission sub1 = new Submission();
        sub1.setOwner(candidate);
        CmoUser merged = entityManager.merge(sub1.getOwner());
        sub1.setOwner(merged);
        sub1.setFilename("new submission");
        sub1.setSubmissionTime(LocalDateTime.now());
        sub1.addExaminer(examiner);
        sub1.setCategory(SubmissionCategory.PRIMARY);
        sub1.setFileDriveId("11AMV5uaXIQVK7XOu0Zkv7YETJJDqlcyc");
        submissionRepository.save(sub1);

//        Grade grade1 = new Grade();
//        grade1.setGrade(70);
//        grade1.setExaminer(examiner);
//        grade1.setSubmission(sub1);
//        gradeRepository.save(grade1);

    }
}
