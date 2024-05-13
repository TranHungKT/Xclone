package unit.com.xclone.userservice.configuration.security;

import com.xclone.userservice.configuration.security.SecurityUserDetails;
import com.xclone.userservice.repository.db.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityUserDetailsTest {
    @Test
    void getAuthorities_ReturnsSingleGrantedAuthority() {
        User user = new User();
        user.setRole("ROLE_USER");

        SecurityUserDetails userDetails = new SecurityUserDetails(user);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
