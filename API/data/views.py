import datetime

from django.http import HttpResponse

from data import tasks
from data.models import Log


def logs(request):
    html = 'Time now: ' + str(datetime.datetime.now()) + '<br /><br />'
    all_logs = Log.objects.all()[:50]

    for log in all_logs:
        html += str(log.time) + '<br/><br/>'

    return HttpResponse(html)


def download_now(request):
    tasks.download_now()
    return HttpResponse('Complete')
