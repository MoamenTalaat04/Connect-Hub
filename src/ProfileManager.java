
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProfileManager {
    private User currentUser ;
    private MainContentCreation contentCreation;
    private static final String database = "profiles.json";
    List<User> profiles = new ArrayList<>();
    private UserDatabase userDatabase;

    public ProfileManager(User currentUser) {
        this.currentUser = currentUser;
        contentCreation=new MainContentCreation();
    }

   /* public List<User> loadProfiles() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(database)) {
            JSONArray profilesArray = (JSONArray) parser.parse(reader);
            for (Object obj : profilesArray) {
                JSONObject profileJson = (JSONObject) obj;
                User profile = new User(
                        (String) profileJson.get("userId"),
                        (String) profileJson.getOrDefault("profilePhotoPath", null),
                        (String) profileJson.getOrDefault("coverPhotoPath", null),
                        (String) profileJson.getOrDefault("bio", ""),
                        (String) profileJson.getOrDefault("hashedPassword", "")
                );
            }
            profiles.add(profile);


        }catch (FileNotFoundException e) {
            System.out.println("Profiles file not found. Starting with an empty list.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return profiles;
    }

    /*public void saveProfiles(List<User> profiles) {
        JSONArray profilesArray = new JSONArray();
        for (User user : profiles) {
            JSONObject saveProfile = new JSONObject();
            saveProfile.put("userId", user.getUserId());
            saveProfile.put("profilePhotoPath", user.getProfilePhotoPath());
            saveProfile.put("coverPhotoPath", user.getCoverPhotoPath());
            saveProfile.put("bio", user.getBio());
            saveProfile.put("hashedPassword", user.getHashedPassword());
            profilesArray.add(saveProfile);
        }
        try (FileWriter writer = new FileWriter(database)) {
            writer.write(profilesArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   /* public void updateProfile(String userId, String profilePhoto, String coverPhoto, String bio, String password) {
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
    }*/

    public User getProfile(String userId) {
        List<User> profiles = userDatabase.readArrayListFromFile();
        return profiles.stream().filter(p -> p.getUserId().equals(userId)).findFirst().orElse(null);
    }




    public ArrayList<Posts> fetchPostsFromUser() {

        ArrayList<Posts> userposts = new ArrayList<>();
        ArrayList<Posts> allPosts = null;
        try {
            allPosts = contentCreation.readPosts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filter posts to include only those from friends
            for (Posts post : allPosts) {
                if (post.getAuthorId().equals(currentUser.getUserId())) {
                    userposts.add(post);
                }

        }
        // Sort posts by timestamp (latest first)
        userposts.sort(Comparator.comparing(Posts::getTimestamp).reversed());
        return userposts;
    }
}    