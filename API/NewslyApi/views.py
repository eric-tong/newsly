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
                                                     'reddit_title AS redditTitle, '
                                                     'article_domain AS articleDomain, '
                                                     'article_top_image AS articleTopImage, '
                                                     'reddit_flair AS redditFlair, '
                                                     'reddit_created AS redditCreated '
                                                     'FROM NewslyApi_redditarticle '
                                                     'ORDER BY time_retrieved DESC, reddit_rank'))

    return HttpResponse(json.dumps(reddit_articles, default=json_default))


def article(request):
    reddit_id = os.path.basename(os.path.normpath(request.get_full_path()))
    return HttpResponse(json.dumps(RedditArticle.objects.get(redditId=reddit_id), default=json_default))


def json_default(self):
    return self.__dict__
