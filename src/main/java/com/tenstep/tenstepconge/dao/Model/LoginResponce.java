package com.tenstep.tenstepconge.dao.Model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponce {
    private final String accessToken;
}
