package org.cmo.cmoportal.submission;

import jakarta.persistence.*;
import lombok.Data;
import org.cmo.cmoportal.cmoUser.CmoUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String filename;

    private String fileDriveId;

    private LocalDateTime submissionTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private CmoUser owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "submission_examiners",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "submission_id", referencedColumnName = "id"
            )
    )
    private List<CmoUser> examiners = new ArrayList<>(3);

    private CmoUser getExaminer(Integer id) {
        return examiners.stream().filter(cmoUser -> cmoUser.getId().equals(id)).findFirst().orElse(null);
    }

    private SubmissionCategory category;

    public void addExaminer(CmoUser examiner) {
        examiners.add(examiner);
    }

}
