package org.cmo.cmoportal.security;

import lombok.RequiredArgsConstructor;
import org.cmo.cmoportal.cmoUser.CmoUser;
import org.cmo.cmoportal.cmoUser.CmoUserRepository;
import org.cmo.cmoportal.cmoUser.MyUserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final CmoUserRepository cmoUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CmoUser byEmail = cmoUserRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        System.out.println("user detail service called");
        MyUserPrincipal principal = new MyUserPrincipal(byEmail);
        return principal;
    }
}
