# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('NewslyApi', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='redditarticle',
            old_name='id',
            new_name='redditId',
        ),
    ]
