from django.db import models


class DatabaseLog(models.Model):
    time = models.DateTimeField(auto_now_add=True)
    articlesAdded = models.IntegerField(default=0)
    success = models.BooleanField(default=False)

    def __str__(self):
        return str(self.time)

    class Meta:
        ordering = ['-time']
