package org.cmo.cmoportal.cmoUser;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResetPasswordDto {

    @NotEmpty(message = "old password cannot be empty")
    private String oldPassword;

    @NotEmpty(message = "new password cannot be empty")
    private String newPassword;

    @NotEmpty(message = "confirm password cannot be empty")
    private String newPassword1;
}
