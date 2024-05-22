package com.xclone.userservice.configuration.security;

import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.repository.db.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> ErrorHelper.buildBadCredentialsException("Email or password is wrong"));

        return new SecurityUserDetails(user);
    }
}
