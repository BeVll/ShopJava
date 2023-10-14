package org.example.dto.account;

import lombok.Data;

@Data
public class GoogleUserInfo {
    private String email;
    private String family_name;
    private String given_name;
    private String id;
    private String locale;
    private String name;
    private String picture;
//    email: "novakvova@gmail.com"
//    family_name: "Novak"
//    given_name:"Volodymyr"
//    id: "101498761612625789045"
//    locale: "ru"
//    name: "Volodymyr Novak"
//    picture: "https://lh3.googleusercontent.com/a/ACg8ocLZCbWiiyfxaXUi8t20WEWUB81BV1Pe2Fnvx807Yt9e8VY=s96-c"
//    verified_email: true
}
