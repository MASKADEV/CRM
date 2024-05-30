package com.crm.rest.util.auth;

import com.crm.rest.security.service.UserDetailsImpl;
import com.crm.rest.util.user.UserCreator;

public class UserDetailsImplCreator {

    public static UserDetailsImpl createUserDetails() {
        return UserDetailsImpl.build(UserCreator.createUser());
    }

}
