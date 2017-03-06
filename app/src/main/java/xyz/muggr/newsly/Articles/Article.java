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
    private long timePosted;

    private String url;

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
        setTimePosted(articleData.getLong("created_utc"));
        setUrl(articleData.getString("url"));
        setTestImage();
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

    public long getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //=======================================================================================
    //endregion

    //region Testing methods
    //=======================================================================================

    private void setTestImage() {
        String[] heroImageUrls = new String[]{
                "https://static.independent.co.uk/s3fs-public/styles/story_large/public/thumbnails/image/2016/03/24/20/16-chuka-umunna-get.jpg",
                "http://ichef.bbci.co.uk/news/660/cpsprodpb/108DE/production/_94960876_c6p7ywcxeaas38r.jpg",
                "http://ichef-1.bbci.co.uk/news/660/cpsprodpb/13286/production/_94907487_annricmalaysiashortlistopennature2017sonyworldphotographyawards.jpg",
                "http://ichef.bbci.co.uk/news/660/cpsprodpb/17DAF/production/_94911779_yuliagrigoryantsarmeniashortlistprofessionaldailylife2017sonyworldphotographyawards.jpg",
                "http://ichef.bbci.co.uk/news/660/cpsprodpb/7A26/production/_94907213_andrsgallardoalbajarspainshortlistopenstilllife2017sonyworldphotographyawards.jpg"
        };

        setHeroImageUrl(heroImageUrls[(int) (Math.random() * heroImageUrls.length)]);
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
