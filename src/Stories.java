import java.time.LocalDateTime;

public class Stories extends Content{
    private LocalDateTime expired;
    public Stories(String content, String authorId, String contentId, String imagePath , LocalDateTime timestamp ) {
        super(content, authorId, contentId,imagePath ,timestamp);
        this.expired = timestamp.plusHours(24);
    }
    public boolean isExpired()
    {
        return  LocalDateTime.now().isAfter(expired);
    }

}
