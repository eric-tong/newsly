from django.db import models


class RedditArticle(models.Model):
    id = models.IntegerField(primary_key=True)
    url = models.URLField(null=True)
    redditTitle = models.CharField(max_length=255, null=True)
    title = models.CharField(max_length=255, null=True, )
    authors = models.CharField(max_length=255, null=True)
    text = models.TextField(null=True)
    topImage = models.URLField(null=True)
    videoUrl = models.URLField(null=True)
    keywords = models.CharField(max_length=255, null=True)