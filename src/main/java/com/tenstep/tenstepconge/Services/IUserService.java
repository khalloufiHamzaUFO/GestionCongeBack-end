package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.Roles;
import com.tenstep.tenstepconge.dao.entities.User;
import java.util.List;

public interface IUserService {
     User findByEmail(String email);
     User updateUser (User user);
     List<User> findUserByRoles( Roles role);
     void changePassword(String email, String newPassword, String oldPssword);
     List<User> getEtudiantUsers();
     User enableOrDisable(String id);
     void saveVerificationToken(String id,String verfi);
     User findByVerificationToken(String verificationToken);
     void disableInactiveAccounts();
}
