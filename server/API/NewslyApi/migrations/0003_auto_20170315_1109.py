# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('NewslyApi', '0002_auto_20170315_1106'),
    ]

    operations = [
        migrations.AlterField(
            model_name='redditarticle',
            name='articleAuthors',
            field=models.TextField(null=True),
        ),
        migrations.AlterField(
            model_name='redditarticle',
            name='articleKeywords',
            field=models.TextField(null=True),
        ),
        migrations.AlterField(
            model_name='redditarticle',
            name='articlePublishDate',
            field=models.TextField(null=True),
        ),
        migrations.AlterField(
            model_name='redditarticle',
            name='articleTitle',
            field=models.TextField(null=True),
        ),
        migrations.AlterField(
            model_name='redditarticle',
            name='redditFlair',
            field=models.TextField(null=True),
        ),
        migrations.AlterField(
            model_name='redditarticle',
            name='redditTitle',
            field=models.TextField(null=True),
        ),
    ]
