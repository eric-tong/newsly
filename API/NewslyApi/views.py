import json

from django.http import HttpResponse
from newspaper import Article


def index(request):
    url = request.GET.get('url', ' ')
    article = Article(url)
    article.download()
    article.parse()
    return HttpResponse(article.title)
