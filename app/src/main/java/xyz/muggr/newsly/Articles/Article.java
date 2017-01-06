package xyz.muggr.newsly.Articles;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    private String uId;
    private String headline;
    private String heroImageUrl;
    private String domain;
    private String flag;
    private String tag;


    //region Constructors
    //=======================================================================================

    public Article() {
    }

    public Article(JSONObject articleData) throws JSONException {
        setHeadline(articleData.getString("title"));
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    //=======================================================================================
    //endregion

}
