package xyz.muggr.newsly.Articles;

public class Article {

    String title;
    long created;
    String domain;
    String url;
    boolean isNsfw;
    String flair;

    //region Getters/Setters
    //=======================================================================================

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isNsfw() {
        return isNsfw;
    }

    public void setNsfw(boolean nsfw) {
        isNsfw = nsfw;
    }

    public String getFlair() {
        return flair;
    }

    public void setFlair(String flair) {
        this.flair = flair;
    }


    //=======================================================================================
    //endregion

}
