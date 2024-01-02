package org.cmo.cmoportal.cmoUser;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserTypeConverter implements Converter<String, UserType> {
    @Override
    public UserType convert(String source) {
        UserType userType = null;
        switch (source) {
            case "candidate":
                userType = UserType.CANDIDATE;
                break;
            case "staff":
                userType = UserType.STAFF;
                break;
            case "examiner":
                userType = UserType.EXAMINER;
                break;
        }
        return userType;
    }
}
