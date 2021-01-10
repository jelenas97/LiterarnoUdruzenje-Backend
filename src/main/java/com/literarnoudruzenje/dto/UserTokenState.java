package com.literarnoudruzenje.dto;

public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private UserDTO userDto;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.userDto = null;
    }

    public UserTokenState(String accessToken, long expiresIn, UserDTO userDto) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.userDto = userDto;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public UserDTO getUser() {
        return userDto;
    }

    public void setUser(UserDTO user) {
        this.userDto = user;
    }


}
