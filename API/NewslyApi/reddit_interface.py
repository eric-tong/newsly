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
            reddit_article.reddit_id = reddit_post_data['id']
            reddit_article.reddit_title = reddit_post_data['title']
            reddit_article.reddit_created = reddit_post_data['created']
            reddit_article.reddit_flair = reddit_post_data['link_flair_text']
            reddit_article.reddit_nsfw = reddit_post_data['over_18']
            reddit_article.reddit_score = reddit_post_data['score']
            reddit_article.article_url = reddit_post_data['url']
            reddit_article.article_domain = reddit_post_data['domain']
            reddit_article.time_retrieved = current_time

            # Get newspaper data
            article = Article(reddit_article.article_url, keep_article_html=True)
            article.download()
            article.parse()
            reddit_article.article_title = article.title
            reddit_article.article_authors = article.authors
            reddit_article.article_text = article.article_html
            reddit_article.article_top_image = article.top_image
            reddit_article.article_publish_date = article.publish_date

            # Get nlp data
            if not article.is_parsed:
                print(reddit_article.article_url)
                continue
            article.nlp()
            reddit_article.article_keywords = article.keywords
            reddit_article.save()
