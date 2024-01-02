package org.cmo.cmoportal.biodata;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.cmo.cmoportal.cmoUser.CmoUser;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "biodata")
public class Biodata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private CmoUser user;
    @NotNull(message = "date of birth is required")
    private LocalDate dateOfBirth;
    @NotEmpty(message = "nationality is required")
    private String nationality;
    @NotEmpty(message = "state of origin is required")
    private String stateOfOrigin;
    @NotEmpty(message = "local government is required")
    private String localGovernment;
    @NotEmpty(message = "religion is required")
    private String religion;
    @NotEmpty(message = "contact number is required")
    private String contactNumber;
    @NotEmpty(message = "school name is required")
    private String nameOfSchool;
    @NotEmpty(message = "school address is required")
    private String schoolAddress;
    @NotEmpty(message = "current school year is required")
    private String schoolClass;
    @NotEmpty(message = "fathers name is required")
    private String fathersName;
    @NotEmpty(message = "mother's name is required")
    private String mothersName;

}
