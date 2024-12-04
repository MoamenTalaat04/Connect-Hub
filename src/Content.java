import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    private String contentId;
    private String authorId;
    private String content;
    private String imagePath;
    private LocalDateTime timestamp;

    public Content() {
    }

    public Content(String content, String authorId, String contentId,String imagePath, LocalDateTime timestamp) {
        this.content = content;
        this.authorId = authorId;
        this.contentId = contentId;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
    }

    public String getContentId() {
        return contentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
