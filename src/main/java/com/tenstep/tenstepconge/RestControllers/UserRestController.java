package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.AuthService;
import com.tenstep.tenstepconge.Services.IUserService;
import com.tenstep.tenstepconge.Services.email.EmailService;
import com.tenstep.tenstepconge.dao.Model.ChangePasswordRequest;
import com.tenstep.tenstepconge.dao.entities.Roles;
import com.tenstep.tenstepconge.dao.entities.User;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;
import com.tenstep.tenstepconge.errors.PasswordDoesNotMatchTheOld;
import com.tenstep.tenstepconge.errors.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("UserRestController")
public class UserRestController {
    IUserService iUserService ;
    private final AuthService authService;
    private EmailService emailService;
    private final UserRepository userRepo;

    @GetMapping("findUserByEmail/{email}")
    User getUserByEmail(@PathVariable("email") String email){
        return iUserService.findByEmail(email);
    }

    @GetMapping("findEtudiantByRole/{role}")
    List<User> findUserByRoles(@PathVariable("role") Roles role){
        return iUserService.findUserByRoles(role);
    }

    @PutMapping("updateUser")
    User editUser( @RequestBody User user){
        return iUserService.updateUser(user);
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        if (request.getEmail() == null ||
                request.getOldPass().isEmpty() ||
                request.getNewPass() == null) {
            System.out.println("Email, old password, and new password are required");
        }

        if (!authService.isOldPasswordCorrect(request.getEmail(), request.getOldPass())) {
            throw new PasswordDoesNotMatchTheOld("The entered old password does not match the current password");
        }
        String subject = "Password Change Notification";
        String body = "Your password has been changed.\nif it wasnt u, \nClick http://localhost:4200/reset-password/" + request.getEmail() + " to change your password.";
        emailService.sendMail(request.getEmail(), subject, body);
        iUserService.changePassword(request.getEmail(), request.getOldPass(), request.getNewPass());
    }

    @PutMapping("/reset-password/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email, @RequestParam String password) {
        try {
            authService.resetPassword(email, password);
            return ResponseEntity.ok("Password reset successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findUsers")
    List<User> getUsers(){
        return iUserService.getEtudiantUsers();
    }

    @PutMapping("toggelUser")
    User enableOrDisable( @RequestParam String email){
        return iUserService.enableOrDisable(email);
    }


    @GetMapping("SendEmail")
    public void sendEmail(@RequestParam("email") String email) {
        User user = iUserService.findByEmail(email);
        if (user.getId() == null) {
            throw new UserNotFoundException("User not found by the provided email");
        } else {
            String subject = "Rest your password";
            String body = "Click on the lick below to rest your password \n http://localhost:4200/reset-password/" + email;
            emailService.sendMail(email, subject, body);
        }

    }
//    @PostMapping("/uploadImg/{idUser}")
//    public User addImg(@RequestParam("file") MultipartFile file , @PathVariable("idUser") long idUser) {
//
//        User user = userRepo.findById(idUser).get();
//        System.out.println("OK");
//
//        try {
//            user.setImagebyte(file.getBytes());
//            userRepo.save(user);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return user;
//    }


}
