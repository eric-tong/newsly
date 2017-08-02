from django.db import models


class Log(models.Model):
    time = models.DateTimeField(auto_now_add=True)
    articlesAdded = models.IntegerField(default=0)

    def __str__(self):
        return str(self.time)

    class Meta:
        ordering = ['-time']
