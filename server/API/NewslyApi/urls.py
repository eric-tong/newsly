from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^cards', views.cards, name='cards'),
    url(r'^article/(?P<reddit_id>[\w.@+-]+)/$', views.article, name='article'),
]
