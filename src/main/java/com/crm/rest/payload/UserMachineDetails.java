package com.crm.rest.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserMachineDetails {

    private String browser;
    private String operatingSystem;
    private String ipAddress;

}
