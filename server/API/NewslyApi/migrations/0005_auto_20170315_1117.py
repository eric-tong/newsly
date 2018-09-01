# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('NewslyApi', '0004_auto_20170315_1116'),
    ]

    operations = [
        migrations.AlterField(
            model_name='redditarticle',
            name='articleUrl',
            field=models.URLField(null=True),
        ),
    ]
