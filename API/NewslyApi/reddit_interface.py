import datetime
import json
import re
import time

import requests
from newspaper import Article, ArticleException

from NewslyApi.models import RedditArticle


class Downloader(object):
    @staticmethod
    def download(reddit_url):
        # Get reddit feed
        reddit_params = {"limit": 100}
        reddit_headers = {'user-agent': 'android:xyz.muggr.newsly.api:v0.0.4 (by /u/regimme)'}
        reddit_feed = requests.get(reddit_url, params=reddit_params, headers=reddit_headers)

        # Parse JSON
        reddit_data = json.loads(reddit_feed.text, encoding='utf-8')

        # Save
        current_time = time.time()
        print('Timestamp: {:%Y-%m-%d %H:%M:%S}'.format(datetime.datetime.now()) + ' Start feed getter')

        for index, reddit_post in enumerate(reddit_data['data']['children']):

            print('Feed ' + str(index))

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
            reddit_article.reddit_rank = index

            # Get newspaper data
            article = Article(reddit_article.article_url, keep_article_html=True)
            article.download()
            try:
                article.parse()
            except ArticleException:
                continue
            reddit_article.article_title = article.title
            reddit_article.article_authors = article.authors
            reddit_article.article_text = Downloader.sanitize_content(article.article_html)
            reddit_article.article_top_image = article.top_image
            reddit_article.article_publish_date = article.publish_date

            # Get nlp data
            if not article.is_parsed:
                print(reddit_article.article_url)
                continue
            article.nlp()
            reddit_article.article_keywords = article.keywords
            reddit_article.save()

        print('Timestamp: {:%Y-%m-%d %H:%M:%S}'.format(datetime.datetime.now()) + ' Complete feed getter')

    @staticmethod
    def sanitize_content(content):

        # Remove unwanted chars
        while '  ' in content:
            content = content.replace('  ', ' ')
        content = content.replace('\n', '')
        content = content.replace('\r', '')
        content = content.replace('\t', '')
        content = content.replace('<b>', '')
        content = content.replace('</b>', '')

        # Content to paragraphs list
        paragraph_list = list()
        inside_p_tag = False
        save_to_list = False
        current_paragraph = ''
        for i in range(0, len(content) - 4):

            if save_to_list:
                if content[i:i + 4] == '</p>':
                    save_to_list = False
                    if len(re.findall(' ', current_paragraph)) > 10:
                        paragraph_list.append(current_paragraph.rstrip())
                    current_paragraph = ''
                else:
                    current_paragraph += content[i]

            elif inside_p_tag and content[i] == '>':
                save_to_list = True
                inside_p_tag = False
            elif content[i:i + 2] == '<p':
                inside_p_tag = True

        # List to html
        # content_html = ''
        # for text in paragraph_list:
        #     content_html += '<br />' + text + '<br />'
        #
        # return content_html

        return paragraph_list
