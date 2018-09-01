package xyz.muggr.newsly.Articles

import android.os.Parcel
import android.os.Parcelable

class Article(var redditId: String, var redditCreated: Long = 0, var redditTitle: String?, var redditFlair: String?, var isRedditNsfw: Boolean = false, var redditScore: Int = 0, // Article fields
              var articleUrl: String?, var articleDomain: String?, var articleTitle: String?, var articleAuthors: String?, var articleSummary: String?, var articleTopImage: String?, var articleKeywords: String?, var articlePublishDate: String?, var timeRetrieved: Long = 0) : Parcelable {

    //region Parcelable methods
    //=======================================================================================

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(redditId)
        parcel.writeLong(redditCreated)
        parcel.writeString(redditTitle)
        parcel.writeString(redditFlair)
        parcel.writeByte(if (isRedditNsfw) 1 else 0)
        parcel.writeInt(redditScore)
        parcel.writeString(articleUrl)
        parcel.writeString(articleDomain)
        parcel.writeString(articleTitle)
        parcel.writeString(articleAuthors)
        parcel.writeString(articleSummary)
        parcel.writeString(articleTopImage)
        parcel.writeString(articleKeywords)
        parcel.writeString(articlePublishDate)
        parcel.writeLong(timeRetrieved)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }

    //=======================================================================================
    //endregion

}
