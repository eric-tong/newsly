# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('NewslyApi', '0006_auto_20170314_2307'),
    ]

    operations = [
        migrations.RenameField(
            model_name='redditarticle',
            old_name='authors',
            new_name='articleAuthors',
        ),
        migrations.RenameField(
            model_name='redditarticle',
            old_name='keywords',
            new_name='articleKeywords',
        ),
        migrations.RenameField(
            model_name='redditarticle',
            old_name='title',
            new_name='articlePublishDate',
        ),
        migrations.RenameField(
            model_name='redditarticle',
            old_name='text',
            new_name='articleText',
        ),
        migrations.RenameField(
            model_name='redditarticle',
            old_name='topImage',
            new_name='articleTopImage',
        ),
        migrations.RenameField(
            model_name='redditarticle',
            old_name='url',
            new_name='articleUrl',
        ),
        migrations.RenameField(
            model_name='redditarticle',
            old_name='created',
            new_name='redditCreated',
        ),
        migrations.RemoveField(
            model_name='redditarticle',
            name='videoUrl',
        ),
        migrations.AddField(
            model_name='redditarticle',
            name='articleTitle',
            field=models.CharField(max_length=255, null=True),
        ),
    ]
