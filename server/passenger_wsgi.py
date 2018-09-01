import sys, os

cwd = os.getcwd()
sys.path.append(cwd)
sys.path.append(cwd + '/API')

INTERP = os.path.expanduser("~/.virtualenvs/NewslyApi/bin/python3")

if sys.executable != INTERP: os.execl(INTERP, INTERP, *sys.argv)

sys.path.insert(0,'$HOME/.virtualenvs/NewslyApi/bin')
sys.path.insert(0,'$HOME/.virtualenvs/NewslyApi/lib/python3.6/site-packages/django')
sys.path.insert(0,'$HOME/.virtualenvs/NewslyApi/lib/python3.6/site-packages')

os.environ['DJANGO_SETTINGS_MODULE'] = 'API.settings'
from django.core.wsgi import get_wsgi_application
application = get_wsgi_application()