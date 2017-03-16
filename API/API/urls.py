from django.conf.urls import include, url
from django.contrib import admin

urlpatterns = [
    url(r'^api/v1/', include('NewslyApi.urls')),
    url(r'^admin/', admin.site.urls),
]