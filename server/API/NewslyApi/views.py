import json

from django.http import HttpResponse

from NewslyApi.models import RedditArticle
from data.reddit_interface import Downloader


def cards(request):
    if request.GET.get('refresh'):
        Downloader.download('https://www.reddit.com/r/worldnews/.json?')

    reddit_articles = RedditArticle.objects.order_by('-time_retrieved', 'reddit_rank')[:100]
    json_out = []
    for reddit_article in reddit_articles:
        json_article = {'redditId': reddit_article.reddit_id,
                        'redditCreated': reddit_article.reddit_created,
                        'redditTitle': reddit_article.reddit_title,
                        'redditFlair': reddit_article.reddit_flair,
                        'articleDomain': reddit_article.article_domain}

        if reddit_article.article_top_image:
            json_article['articleTopImage'] = "http://newsly.muggr.xyz" + reddit_article.article_top_image.url
        else:
            json_article['articleTopImage'] = reddit_article.article_top_image_url

        json_out.append(json_article)

    json_out = {'success': True, 'content': json_out}

    return HttpResponse(json.dumps(json_out, default=json_default))


def article(request, reddit_id):
    reddit_article = RedditArticle.objects.get(reddit_id=reddit_id)
    json_article = {'redditId': reddit_article.reddit_id,
                    'redditCreated': reddit_article.reddit_created,
                    'redditTitle': reddit_article.reddit_title,
                    'redditFlair': reddit_article.reddit_flair,
                    'articleTitle': reddit_article.article_title,
                    'articleUrl': reddit_article.article_url,
                    'articleAuthors': reddit_article.article_authors,
                    'articleDomain': reddit_article.article_domain,
                    'articlePublishDate': reddit_article.article_publish_date}

    if reddit_article.article_top_image:
        json_article['articleTopImage'] = "http://newsly.muggr.xyz" + reddit_article.article_top_image.url
    else:
        json_article['articleTopImage'] = reddit_article.article_top_image_url

    if not reddit_article.article_summary == "[]":
        json_article['articleSummary'] = reddit_article.article_summary
    else:
        json_article['articleSummary'] = reddit_article.article_text

    json_out = {'success': True, 'content': [json_article]}

    return HttpResponse(json.dumps(json_out, default=json_default))


def json_default(self):
    return self.__dict__
