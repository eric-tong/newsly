import json

import requests
import time

from newspaper import Article

from NewslyApi.models import RedditArticle


class Downloader(object):

    @staticmethod
    def download():
        # Get reddit feed
        reddit_params = {"limit": 10}
        reddit_headers = {'user-agent': 'android:xyz.muggr.newsly.api:v0.0.3 (by /u/regimme)'}
        reddit_url = 'https://www.reddit.com/r/worldnews/.json?limit=10'
        reddit_feed = requests.get(reddit_url, params=reddit_params, headers=reddit_headers)

        # Parse JSON
        reddit_data = json.loads(reddit_feed.text, encoding='utf-8')

        # Save
        current_time = time.time()

        for reddit_post in reddit_data['data']['children']:
            # Add reddit data
            reddit_post_data = reddit_post['data']
            reddit_article = RedditArticle()
            reddit_article.redditId = reddit_post_data['id']
            reddit_article.redditTitle = reddit_post_data['title']
            reddit_article.redditCreated = reddit_post_data['created']
            reddit_article.redditFlair = reddit_post_data['link_flair_text']
            reddit_article.redditNsfw = reddit_post_data['over_18']
            reddit_article.redditScore = reddit_post_data['score']
            reddit_article.articleUrl = reddit_post_data['url']
            reddit_article.articleDomain = reddit_post_data['domain']
            reddit_article.timeRetrieved = current_time

            # Get newspaper data
            article = Article(reddit_article.articleUrl, keep_article_html=True)
            article.download()
            if not article.is_downloaded:
                print(reddit_article.articleUrl)
                continue
            article.parse()
            reddit_article.articleTitle = article.title
            reddit_article.articleAuthors = article.authors
            reddit_article.articleText = article.article_html
            reddit_article.articleTopImage = article.top_image
            reddit_article.articlePublishDate = article.publish_date

            # Get nlp data
            if not article.is_parsed:
                print(reddit_article.articleUrl)
                continue
            article.nlp()
            reddit_article.articleKeywords = article.keywords
            reddit_article.save()
