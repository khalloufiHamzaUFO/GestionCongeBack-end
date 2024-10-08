package com.tenstep.tenstepconge.Services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tenstep.tenstepconge.Services.email.EmailService;
import com.tenstep.tenstepconge.dao.entities.Roles;
import com.tenstep.tenstepconge.dao.entities.User;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;
import com.tenstep.tenstepconge.errors.UserNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class UserService implements IUserService {
    private final UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public User findByEmail(String email) {
        if(this.userRepo.findByEmail(email) != null){

            return this.userRepo.findByEmail(email);
        }else{
            User user = new User();
            user.setId(null);
            return user ;
        }
    }

    @JsonIgnore
    @Override
    public User updateUser(User user) {
        System.out.println("test");
        if (userRepo.existsById(user.getId())) {
            System.out.println("test");
            User existing = userRepo.findUserById(user.getId());
            user.setPassword(existing.getPassword());
            user.setEmail(existing.getEmail());
            user.setRole(existing.getRole());
            user.setUpdatedAt(new Date());

            System.out.println(user.toString());
            System.out.println("USER is = "+user);
            return userRepo.save(user);
        } else {
            System.out.println("exeption");
            throw new RuntimeException("User not found with id: " + user.getId());
        }
    }

    @Override
    public List<User> findUserByRoles( Roles role) {
        List<User> etudiants = this.userRepo.findUsersByRole(role);
        List<User> finalEtudiants = new ArrayList<>();
        for(User etudiant : etudiants){
            boolean test = false ;
            if(!test){
                finalEtudiants.add(etudiant);
            }
        }
        return finalEtudiants;
    }

//    @Override
//    public boolean changePassword(String email, String newPassword, String oldPssword) {
//        if(userRepo.existsByEmail(email)){
//            User existing = userRepo.findByEmail(email);
//            if(existing.getPassword()==newPassword){
//                existing.setPassword(newPassword);
//            }
//            return true;
//        }else
//            return false;
//
//    }

    @Override
    public void saveVerificationToken(String id, String verfi) {
        User u = userRepo.findUserById(id);
        u.setVerificationToken(verfi);
        userRepo.save(u);
    }

    @Override
    public User findByVerificationToken(String verificationToken) {
        return userRepo.findByVerificationToken(verificationToken);
    }


    @Override
    public List<User> getEtudiantUsers() {
        System.out.println("Tqqqest");
        List<User> allUsers = userRepo.findAll();
        return allUsers;
    }



    @Override
    public User enableOrDisable(String email) {
        User u = userRepo.findByEmail(email);
        if (u != null) {
            boolean isEnabled = u.isEnabled();
            u.setEnabled(!isEnabled);
            userRepo.save(u);
            return u;
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        if(userRepo.existsByEmail(email)) {
            User user = userRepo.findByEmail(email);
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepo.save(user);
            } else {
                throw new BadCredentialsException("Incorrect old password");
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    @Override
    public void disableInactiveAccounts() {
        List<User> inactiveUsers = userRepo.findByLastLoginBefore(LocalDate.now().minusDays(90));
        for (User user : inactiveUsers) {
            user.setEnabled(false);
            userRepo.save(user);
        }
    }






//    public boolean verify(String verificationCode) {
//        User user = userRepo.findByVerificationCode(verificationCode);
//
//        if (user == null || user.isEnabled()) {
//            return false;
//        } else {
//            user.setVerificationCode(null);
//            user.setEnabled(true);
//            repo.save(user);
//
//            return true;
//        }
//    }


}
