package org.cmo.cmoportal.grade;

import jakarta.persistence.*;
import lombok.Data;
import org.cmo.cmoportal.cmoUser.CmoUser;
import org.cmo.cmoportal.submission.Submission;

@Data
@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String remark;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "examiner_id", referencedColumnName = "id")
    private CmoUser examiner;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "submission_id", referencedColumnName = "id")
    private Submission submission;
    private Integer grade;
}
