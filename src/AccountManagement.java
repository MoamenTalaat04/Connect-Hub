import java.io.IOException;

public class AccountManagement {
    private final com.connecthub.service.AccountService accountService;

    public AccountManagement( UserDatabase userDatabase) {
        this.accountService = ConnectHubContext.factory().accountService();
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
            return accountService.signUp(email, userName, password, dateOfBirth, bio, coverPhotoPath, profilePhotoPath);
    }
    public boolean login(String email,String password) throws IOException {
        return accountService.login(email, password);
    }
/*public boolean logout(User user){
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
}*/

}
