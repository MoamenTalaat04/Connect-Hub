import java.time.LocalDateTime;

public class Stories extends Content{


    public Stories()
    {
        super();
    }
    public Stories(String content, String authorId, String contentId, String imagePath , LocalDateTime timestamp ) {
        super(content, authorId, contentId,imagePath ,timestamp);

    }
    public boolean isExpired()
    {
        return  LocalDateTime.now().isAfter(getTimestamp().plusHours(24));
    }

}
