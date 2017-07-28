import json
import os

from django.http import HttpResponse

from NewslyApi.models import RedditArticle
from NewslyApi.reddit_interface import Downloader


def cards(request):
    if request.GET.get('refresh'):
        Downloader.download()

    reddit_articles = list(RedditArticle.objects.all())

    return HttpResponse(json.dumps(reddit_articles, default=json_default))


def article(request):
    reddit_id = os.path.basename(os.path.normpath(request.get_full_path()))
    return HttpResponse(json.dumps(RedditArticle.objects.get(redditId=reddit_id), default=json_default))


def json_default(self):
    return self.__dict__
