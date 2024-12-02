import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class AccountManagement {
    private String dataBaseFile;
    private ArrayList<User> users;



    public AccountManagement(String dataBaseFile) {
        this.dataBaseFile = dataBaseFile;
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

    private void loadUsers(){
        //when called ----> load users from database ---transform--> User obj ----store---> arrayList of User
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayList<User> usersList = objectMapper.readValue(new File(/*Json file path*/""), new TypeReference<ArrayList<User>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUsers(){
        //when called ----> transforms evey obj in User ArrayList to line string -----> and saves each line string in the database
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(/*Json file path*/" "),users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean signUp(String email,String userName,String password,String dateOfBirth,String bio,String coverPhotoPath,String profilePhotoPath){
            try{
                String hashedPassword = hashPassword(password);
                String id =UUID.randomUUID().toString()+users.size(); //generates unique id for each user
                User user =new User(id,null,null,null,"Online",userName,email,hashedPassword,coverPhotoPath,bio,profilePhotoPath);
                if(!users.isEmpty()){
                    loadUsers();
                }
                users.add(user);
                saveUsers();
                return true;
            }
             catch (NoSuchAlgorithmException e) {
                System.out.println("No Such Algorithm Exception !!");
                 return false;
            }
    }
    public boolean login(String email,String password){
        if(!users.isEmpty()){
            loadUsers();
        }
        for (int i=0;i<users.size();i++){

            try {
                if(users.get(i).getEmail().equals(email)&&users.get(i).getHashedPassword().equals(hashPassword(password))){
                    users.get(i).setStatus("Online");
                    saveUsers();
                    return true;
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
public boolean logout(User user){
   try{
       //sets user status to offline
       user.setStatus("Offline");
       //saves any changes happen
       saveUsers();
       return true;
   }
   catch (Exception e){
       return false;
   }
}

}
