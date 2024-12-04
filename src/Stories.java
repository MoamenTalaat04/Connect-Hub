import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stories extends Content{

    private boolean expired;

    public Stories()
    {
        super();
    }
    public Stories(String content, String authorId, String contentId, String imagePath , LocalDateTime timestamp ) {
        super(content, authorId, contentId,imagePath ,timestamp);
        this.expired = timestamp.isBefore(LocalDateTime.now().minusHours(24));
    }
    public boolean isExpired()
    {
        return  LocalDateTime.now().isAfter(getTimestamp().plusHours(24));
    }
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
