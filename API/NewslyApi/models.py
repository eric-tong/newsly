from django.db import models


class RedditArticle(models.Model):
    # Reddit fields
    redditId = models.CharField(max_length=255, primary_key=True, default='', unique=True)
    redditCreated = models.IntegerField(default=0)
    redditTitle = models.TextField(null=True)
    redditFlair = models.TextField(null=True)
    redditNsfw = models.BooleanField(default=False)
    redditScore = models.IntegerField(default=0)

    # Article fields
    articleUrl = models.URLField(null=True)
    articleDomain = models.URLField(null=True)
    articleTitle = models.TextField(null=True)
    articleAuthors = models.TextField(null=True)
    articleText = models.TextField(null=True)
    articleTopImage = models.URLField(null=True)
    articleKeywords = models.TextField(null=True)
    articlePublishDate = models.TextField(null=True)
    timeRetrieved = models.IntegerField(default=0)

    def __str__(self):
        return self.redditTitle
