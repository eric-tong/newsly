package xyz.muggr.newsly.Articles;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    // Reddit fields
    String redditId;
    long redditCreated;
    String redditTitle;
    String redditFlair;
    boolean redditNsfw;
    int redditScore;

    // Article fields
    String articleUrl;
    String articleDomain;
    String articleTitle;
    String articleAuthors;
    String articleText;
    String articleTopImage;
    String articleKeywords;
    String articlePublishDate;
    long timeRetrieved;

    //region Getters/Setters
    //=======================================================================================

    public String getRedditId() {
        return redditId;
    }

    public void setRedditId(String redditId) {
        this.redditId = redditId;
    }

    public long getRedditCreated() {
        return redditCreated;
    }

    public void setRedditCreated(long redditCreated) {
        this.redditCreated = redditCreated;
    }

    public String getRedditTitle() {
        return redditTitle;
    }

    public void setRedditTitle(String redditTitle) {
        this.redditTitle = redditTitle;
    }

    public String getRedditFlair() {
        return redditFlair;
    }

    public void setRedditFlair(String redditFlair) {
        this.redditFlair = redditFlair;
    }

    public boolean isRedditNsfw() {
        return redditNsfw;
    }

    public void setRedditNsfw(boolean redditNsfw) {
        this.redditNsfw = redditNsfw;
    }

    public int getRedditScore() {
        return redditScore;
    }

    public void setRedditScore(int redditScore) {
        this.redditScore = redditScore;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleDomain() {
        return articleDomain;
    }

    public void setArticleDomain(String articleDomain) {
        this.articleDomain = articleDomain;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleAuthors() {
        return articleAuthors;
    }

    public void setArticleAuthors(String articleAuthors) {
        this.articleAuthors = articleAuthors;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public String getArticleTopImage() {
        return articleTopImage;
    }

    public void setArticleTopImage(String articleTopImage) {
        this.articleTopImage = articleTopImage;
    }

    public String getArticleKeywords() {
        return articleKeywords;
    }

    public void setArticleKeywords(String articleKeywords) {
        this.articleKeywords = articleKeywords;
    }

    public String getArticlePublishDate() {
        return articlePublishDate;
    }

    public void setArticlePublishDate(String articlePublishDate) {
        this.articlePublishDate = articlePublishDate;
    }

    public long getTimeRetrieved() {
        return timeRetrieved;
    }

    public void setTimeRetrieved(long timeRetrieved) {
        this.timeRetrieved = timeRetrieved;
    }


    //=======================================================================================
    //endregion

    //region Parcelable methods
    //=======================================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.redditId);
        dest.writeLong(this.redditCreated);
        dest.writeString(this.redditTitle);
        dest.writeString(this.redditFlair);
        dest.writeByte(this.redditNsfw ? (byte) 1 : (byte) 0);
        dest.writeInt(this.redditScore);
        dest.writeString(this.articleUrl);
        dest.writeString(this.articleDomain);
        dest.writeString(this.articleTitle);
        dest.writeString(this.articleAuthors);
        dest.writeString(this.articleText);
        dest.writeString(this.articleTopImage);
        dest.writeString(this.articleKeywords);
        dest.writeString(this.articlePublishDate);
        dest.writeLong(this.timeRetrieved);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.redditId = in.readString();
        this.redditCreated = in.readLong();
        this.redditTitle = in.readString();
        this.redditFlair = in.readString();
        this.redditNsfw = in.readByte() != 0;
        this.redditScore = in.readInt();
        this.articleUrl = in.readString();
        this.articleDomain = in.readString();
        this.articleTitle = in.readString();
        this.articleAuthors = in.readString();
        this.articleText = in.readString();
        this.articleTopImage = in.readString();
        this.articleKeywords = in.readString();
        this.articlePublishDate = in.readString();
        this.timeRetrieved = in.readLong();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    //=======================================================================================
    //endregion

}
