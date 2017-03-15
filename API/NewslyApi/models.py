from django.db import models


class RedditArticle(models.Model):
    redditCreated = models.IntegerField(primary_key=True, unique=True)
    redditTitle = models.CharField(max_length=255, null=True)
    redditScore = models.IntegerField(default=0)
    articleUrl = models.URLField(null=True, unique=True)
    articleTitle = models.CharField(max_length=255, null=True, )
    articleAuthors = models.CharField(max_length=255, null=True)
    articleText = models.TextField(null=True)
    articleTopImage = models.URLField(null=True)
    articleKeywords = models.CharField(max_length=255, null=True)
    articlePublishDate = models.CharField(max_length=255, null=True)
    timeRetrieved = models.IntegerField(default=0)

    def __str__(self):
        return self.redditTitle
