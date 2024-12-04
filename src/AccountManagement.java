import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
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

    private String hashPassword(String password)
            throws NoSuchAlgorithmException {
        MessageDigest encryptor = MessageDigest.getInstance("SHA-256");// encryptor is an object that we will use in order to encrypt the password string
        byte[] hashedBytes = encryptor.digest(password.getBytes()); // hashedBytes will store the encrypted password as bytes where each element in the array is an encrypted byte for a char. in the password
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b)); // the stringBuilder will convert each byte in the array to 2 hexadecimal chars
        }
        return stringBuilder.toString(); // returns the string that holds the encrypted password
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
                ArrayList<User> users=userDatabase.readUsersFromFile();
                String hashedPassword = hashPassword(password);
                String id =""+(users.size()+1); //generates unique id for each user
                User user =new User(id,null,null,null,"Online",dateOfBirth,userName,email,hashedPassword,coverPhotoPath,bio,profilePhotoPath);
                userDatabase.saveUserToFile(user);
                return true;
            }
             catch (NoSuchAlgorithmException e) {
                System.out.println("No Such Algorithm Exception !!");
                 return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public boolean login(String email,String password) throws IOException {
        String inputPassword = null;
        try {
            inputPassword = hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Map<String,String> loginMap=userDatabase.readMapFromUsers();
        String storedPassword= loginMap.get(email);
        if (inputPassword.equals(storedPassword)) return true;
        else return false;
    }
public boolean logout(User user){
   try{
       //sets user status to offline
       user.setStatus("Offline");
       //saves any changes happen
       userDatabase.saveUserToFile(user);
       return true;
   }
   catch (Exception e){
       return false;
   }
}

}
