package com.rr.security.authserver.config;

import com.rr.security.authserver.service.CustomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
//DaoAuthenticationProvider
public class UserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    //private final Logger log = LoggerFactory.getLogger(AccountAuthenticatoinProvider.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CustomDetailsService customDetailsService;

    private volatile String userNotFoundEncodedPassword;

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        if(usernamePasswordAuthenticationToken.getCredentials() == null || userDetails.getPassword() == null) {
            throw new BadCredentialsException("Credentials may not be null.");
        } else if(!passwordEncoder.matches((String) usernamePasswordAuthenticationToken.getCredentials(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    protected final UserDetails retrieveUser(String username,
                                             UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        prepareTimingAttackProtection();
        try {
            //UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
            UserDetails loadedUser = customDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException ex) {
            mitigateAgainstTimingAttack(authentication);
            throw ex;
        }
        catch (InternalAuthenticationServiceException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        }
    }

    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }
    }

}
