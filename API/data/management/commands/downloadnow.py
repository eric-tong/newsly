from django.core.management.base import BaseCommand, CommandError

from data.reddit_interface import Downloader


class Command(BaseCommand):
    print('Start download')

    def handle(self, *args, **options):
        reddit_url = 'https://www.reddit.com/r/worldnews/.json?'
        Downloader.download(reddit_url)
