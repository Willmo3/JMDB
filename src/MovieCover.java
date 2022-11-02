
/**
 * Movie cover class.
 * @author Immanuel Semelfort
 *
 */
public class MovieCover {

    private String url;
    
    /**
     * movieCover constructor
     * @param url of the image
     */
    public MovieCover(String url) {
        this.url = url;
    }
    
    /**
     * set the link if changed
     * @param link of the image
     */
    public void setUrl(String link) {
        this.url = link;
    }
    
    /**
     * gets the URL
     * @return the URL
     */
    public String getUrl() {
        return this.url;
    }
    
}