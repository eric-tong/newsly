# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('NewslyApi', '0003_auto_20170315_1109'),
    ]

    operations = [
        migrations.AlterField(
            model_name='redditarticle',
            name='articleDomain',
            field=models.URLField(null=True),
        ),
    ]
