package com.tenstep.tenstepconge.dao.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    private String oldPass;
    private String newPass;
    private String email;
}
