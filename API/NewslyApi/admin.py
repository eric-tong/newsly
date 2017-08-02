from django.contrib import admin

from NewslyApi.models import RedditArticle
from data.models import DatabaseLog

admin.site.register(RedditArticle)
admin.site.register(DatabaseLog)
