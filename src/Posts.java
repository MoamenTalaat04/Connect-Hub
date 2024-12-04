import java.time.LocalDateTime;

public class Posts extends Content{
    public Posts(){
        super();
    }
    public Posts(String content, String authorId, String contentId,String imagePath ,LocalDateTime timestamp) {
        super(content, authorId, contentId,imagePath ,timestamp);
    }
}
