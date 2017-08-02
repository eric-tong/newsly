from django.contrib import admin

from NewslyApi.models import RedditArticle
from data.models import Log

admin.site.register(RedditArticle)
admin.site.register(Log)
