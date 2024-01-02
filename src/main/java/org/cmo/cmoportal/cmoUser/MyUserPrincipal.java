package org.cmo.cmoportal.cmoUser;

import org.cmo.cmoportal.cmoUser.CmoUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class MyUserPrincipal implements UserDetails {

    private CmoUser cmoUser;

    public MyUserPrincipal(CmoUser hogwartsUser) {
        this.cmoUser = hogwartsUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
         * convert a user roles from space delimited string to a list of simpleGrantedAuthority objects
         * */
        return Arrays.stream(StringUtils.tokenizeToStringArray(cmoUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return cmoUser.getPassword();
    }

    @Override
    public String getUsername() {
        return cmoUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return cmoUser.isEnabled();
    }

    public CmoUser getCmoUser() {
        return cmoUser;
    }
}