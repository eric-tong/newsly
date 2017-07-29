import schedule
import time

from NewslyApi import reddit_interface


def start():
    reddit_url = 'https://www.reddit.com/r/worldnews/.json?limit=20'
    schedule.every(10).minutes.do(reddit_interface.Downloader.download(reddit_url))

    while True:
        schedule.run_pending()
        time.sleep(1)
