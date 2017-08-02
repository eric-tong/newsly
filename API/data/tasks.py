import time

import schedule

from data.reddit_interface import Downloader


def start():
    print('Start reddit interface')

    reddit_url = 'https://www.reddit.com/r/worldnews/.json?'

    try:
        schedule.every(10).minutes.do(Downloader.download, reddit_url)

        while True:
            schedule.run_pending()
            time.sleep(60)
    except:
        start()


def download_now():
    reddit_url = 'https://www.reddit.com/r/worldnews/.json?'
    Downloader.download(reddit_url)
