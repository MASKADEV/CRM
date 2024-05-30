package com.crm.pfe.util.auth;

import com.crm.pfe.security.service.UserDetailsImpl;
import com.crm.pfe.util.user.UserCreator;

public class UserDetailsImplCreator {

    public static UserDetailsImpl createUserDetails() {
        return UserDetailsImpl.build(UserCreator.createUser());
    }

}
