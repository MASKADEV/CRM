package com.crm.rest.util;

import com.crm.rest.security.service.UserDetailsImpl;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

import static com.crm.rest.util.auth.UserDetailsImplCreator.createUserDetails;

public class MockUtils {

    public static void mockSecurityContextHolder(boolean nullPrincipal) {
        Authentication auth = Mockito.mock(Authentication.class);

        UserDetailsImpl userDetails = nullPrincipal ? null : createUserDetails();

        Mockito
                .when(auth.getPrincipal())
                .thenReturn(userDetails);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito
                .when(securityContext.getAuthentication())
                .thenReturn(auth);

        SecurityContextHolder.setContext(securityContext);
    }

    public static void mockSecurityContextHolder() {
        mockSecurityContextHolder(false);
    }

    public static HttpServletRequest mockUserMachineInfo() {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        Mockito
                .when(httpServletRequest.getHeader(""))
                .thenReturn("");

        return httpServletRequest;
    }

}
