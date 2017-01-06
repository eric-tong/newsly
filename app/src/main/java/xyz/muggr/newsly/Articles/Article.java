package xyz.muggr.newsly.Articles;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    private String uId;
    private String headline;
    private String heroImageUrl;
    private String domain;
    private String tag;
    private int flags;


    //region Constructors
    //=======================================================================================

    public Article() {
    }

    public Article(JSONObject articleData) throws JSONException {
        setHeadline(articleData.getString("title"));
        setDomain(articleData.getString("domain"));
        if (!articleData.isNull("link_flair_text"))
            setTag(articleData.getString("link_flair_text"));
        setFlags(
                (articleData.getLong("score") > 2000L ? Flag.IS_TOP_NEWS : 0) |
                        (articleData.getBoolean("over_18") ? Flag.IS_NSFW : 0)
        );
    }


    //=======================================================================================
    //endregion

    //region Getters/Setters
    //=======================================================================================

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    //=======================================================================================
    //endregion

    //region Keys
    //=======================================================================================

    public static class Flag {
        public static final int IS_TOP_NEWS = 0b01;
        public static final int IS_NSFW = 0b10;
    }

    //=======================================================================================
    //endregion

}
