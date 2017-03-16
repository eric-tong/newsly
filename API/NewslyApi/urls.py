from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^cards', views.cards, name='cards'),
    url(r'^article/*', views.article, name='article'),
]
