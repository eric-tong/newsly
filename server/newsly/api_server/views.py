from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse

from api_server.models import *

# Create your views here.

def index(request):
    return HttpResponse("Newsly API Server")

def register(request):
    new_client = Client()
    new_client.save()
    response = {
            'status': {
                'code' : 200,
                'detail': "OK"
                },
            'uuid': new_client.uuid.hex
            }
    return JsonResponse(response)
