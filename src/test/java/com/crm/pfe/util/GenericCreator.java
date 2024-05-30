package com.crm.pfe.util;

import com.crm.pfe.payload.UserMachineDetails;

public class GenericCreator {

    public static final String BROWSER = "browser-test";
    public static final String OPERATING_SYSTEM = "os-test";
    public static final String ID_ADDRESS = "ip-test";

    public static UserMachineDetails createUserMachineDetails() {
        return UserMachineDetails
                .builder()
                .operatingSystem(OPERATING_SYSTEM)
                .ipAddress(ID_ADDRESS)
                .browser(BROWSER)
                .build();
    }

}
