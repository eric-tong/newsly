from django.db import models


class RedditArticle(models.Model):
    # Reddit fields
    reddit_id = models.CharField(max_length=255, primary_key=True, default='', unique=True)
    reddit_created = models.IntegerField(default=0)
    reddit_title = models.TextField(null=True)
    reddit_flair = models.TextField(null=True)
    reddit_nsfw = models.BooleanField(default=False)
    reddit_score = models.IntegerField(default=0)
    reddit_rank = models.IntegerField(default=0)

    # Article fields
    article_url = models.URLField(null=True)
    article_domain = models.URLField(null=True)
    article_title = models.TextField(null=True)
    article_authors = models.TextField(null=True)
    article_text = models.TextField(null=True)
    article_summary = models.TextField(null=True)
    article_top_image = models.ImageField(upload_to='article-images', null=True)
    article_top_image_url = models.URLField(null=True)
    article_keywords = models.TextField(null=True)
    article_publish_date = models.TextField(null=True)
    time_retrieved = models.IntegerField(default=0)

    def __str__(self):
        return self.reddit_title

    class Meta:
        ordering = ['-time_retrieved', 'reddit_rank', ]
