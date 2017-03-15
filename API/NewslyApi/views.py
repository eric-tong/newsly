import json
import time
import requests
from django.http import HttpResponse
from newspaper import Article

from NewslyApi.models import RedditArticle


def index(request):
    # Get reddit feed
    reddit_params = {"limit": 10}
    reddit_headers = {'user-agent': 'android:xyz.muggr.newsly.api:v0.0.1 (by /u/regimme)'}
    reddit_url = 'https://www.reddit.com/r/worldnews/.json?top-=limit=100'
    reddit_feed = requests.get(reddit_url, params=reddit_params, headers=reddit_headers)

    # Parse JSON
    reddit_data = json.loads(reddit_feed.text, encoding='utf-8')

    # Save
    current_time = time.time()

    for reddit_post in reddit_data['data']['children']:
        # Add reddit data
        reddit_post_data = reddit_post['data']
        reddit_article = RedditArticle()
        reddit_article.redditCreated = reddit_post_data['created']
        reddit_article.articleUrl = reddit_post_data['url']
        reddit_article.timeRetrieved = current_time
        reddit_article.redditTitle = reddit_post_data['title']
        reddit_article.redditScore = reddit_post_data['score']

        # Get newspaper data
        article = Article(reddit_article.articleUrl, keep_article_html=True)
        article.download()
        if not article.is_downloaded:
            continue
        article.parse()
        article.nlp()
        reddit_article.articleTitle = article.title
        reddit_article.articleAuthors = article.authors
        reddit_article.articleText = article.article_html
        reddit_article.articleTopImage = article.top_image
        reddit_article.articleKeywords = article.keywords
        reddit_article.articlePublishDate = article.publish_date
        reddit_article.save()

    # Get dictionary
    def json_default(self):
        return self.__dict__

    return HttpResponse(json.dumps(list(RedditArticle.objects.all()), default=json_default))
