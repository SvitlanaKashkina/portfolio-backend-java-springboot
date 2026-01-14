package com.kashkina.portfolio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {

    private String fullName;
    private String role;
    private String shortBio;
    private String aboutText;
    private String location;
    private String email;
    private String githubUrl;
    private String linkedinUrl;
}
