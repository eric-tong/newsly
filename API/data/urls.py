from django.conf.urls import url, include

from data import views, tasks

urlpatterns = [
    url(r'^logs', views.logs),
    url(r'^download', views.download_now),
]