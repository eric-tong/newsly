import json
import os

from django.http import HttpResponse

from NewslyApi.models import RedditArticle
from NewslyApi.reddit_interface import Downloader


def cards(request):
    if request.GET.get('refresh'):
        Downloader.download('https://www.reddit.com/r/worldnews/.json?limit=20')

    reddit_articles = list(RedditArticle.objects.raw('SELECT '
                                                     'reddit_id, '
                                                     'reddit_id AS redditId, '
                                                     'reddit_created AS redditCreated, '
                                                     'reddit_title AS redditTitle, '
                                                     'reddit_flair AS redditFlair, '
                                                     'article_domain AS articleDomain, '
                                                     'article_top_image AS articleTopImage '
                                                     'FROM NewslyApi_redditarticle '
                                                     'ORDER BY time_retrieved DESC, reddit_rank'))

    return HttpResponse(json.dumps(reddit_articles, default=json_default))


def article(request, reddit_id):
    reddit_article = list(RedditArticle.objects.raw('SELECT '
                                                    'reddit_id, '
                                                    'reddit_id AS redditId, '
                                                    'reddit_created AS redditCreated, '
                                                    'reddit_title AS redditTitle, '
                                                    'reddit_flair AS redditFlair, '
                                                    'article_url AS articleUrl, '
                                                    'article_domain AS articleDomain, '
                                                    'article_text AS articleText, '
                                                    'article_top_image AS articleTopImage, '
                                                    'article_publish_date AS articlePublishDate '
                                                    'FROM NewslyApi_redditarticle WHERE reddit_id = %s', [reddit_id]))
    return HttpResponse(json.dumps(reddit_article[0], default=json_default))


def json_default(self):
    return self.__dict__
