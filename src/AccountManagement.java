import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class AccountManagement {
    private  UserDatabase userDatabase;



    public AccountManagement( UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }


    /*
    public void updateProfile(String userId, String profilePhoto, String coverPhoto, String bio, String password) {
        List<User> profiles = loadProfiles();
        for (User user : profiles) {
            if (user.getUserId().equals(userId)) {
                if (profilePhoto != null) user.setProfilePhotoPath(profilePhoto);
                if (coverPhoto != null) user.setCoverPhotoPath(coverPhoto);
                if (bio != null) user.setBio(bio);
                if (password != null) user.setHashedPassword(PasswordUtils.hashPassword(password));
                break;
            }
        }
        saveProfiles(profiles);
    }

    public User getProfile(String userId) {
        List<User> profiles = loadProfiles();
        return profiles.stream().filter(p -> p.getUserId().equals(userId)).findFirst().orElse(null);
    }

     */
    /*
    private void loadUsers(){
        //when called ----> load users from database ---transform--> User obj ----store---> arrayList of User
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayList<User> usersList = objectMapper.readValue(new File(""), new TypeReference<ArrayList<User>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    */
/*
    private void saveUsers(){
        //when called ----> transforms evey obj in User ArrayList to line string -----> and saves each line string in the database
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(" "),users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

 */

    public boolean signUp(String email,String userName,String password,String dateOfBirth,String bio,String coverPhotoPath,String profilePhotoPath){
            try{
                String hashedPassword = HashPassword.hashPassword(password);
                String id =UUID.randomUUID().toString()+users.size(); //generates unique id for each user
                User user =new User(id,null,null,null,"Online",dateOfBirth,userName,email,hashedPassword,coverPhotoPath,bio,profilePhotoPath);
                userDatabase.saveUserToFile(user,userDatabase.userDatabaseFile);
                return true;
            }
             catch (NoSuchAlgorithmException e) {
                System.out.println("No Such Algorithm Exception !!");
                 return false;
            }
    }
    public boolean login(String email,String password){
        String inputPassword = null;
        try {
            inputPassword = HashPassword.hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Map<String,String> loginMap=userDatabase.readMapFromFile();
        String storedPassword= loginMap.get(email);
        if (inputPassword.equals(storedPassword)) return true;
        else return false;
    }
public boolean logout(User user){
   try{
       //sets user status to offline
       user.setStatus("Offline");
       //saves any changes happen
       userDatabase.saveUserToFile(user,userDatabase.userDatabaseFile);
       return true;
   }
   catch (Exception e){
       return false;
   }
}

}
