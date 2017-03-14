from django.http import HttpResponse
from newspaper import Article


def index(request):
    url = request.GET.get('url', ' ')
    article = Article(url)
    article.download()
    return HttpResponse(article.html)
