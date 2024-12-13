import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class notificationData {
    private String photopath;
    private String from;
    private String description;
    private String date;


    public notificationData(){}
    public notificationData(
             String from,
             String photopath,
             String description,
             String date
    ) {
        this.photopath = photopath;
        this.from = from;
        this.description = description;
        this.date = date;
    }

    // Getters and Setters
    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public String getfrom() {
        return from;
    }

    public void setfrom(String name) {
        this.from = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
