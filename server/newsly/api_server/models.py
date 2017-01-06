from django.db import models
from django.utils.text import Truncator
import uuid

class Client(models.Model):
    uuid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=256)

    def __str__(self):
        return "'{0}' <{1}>".format(self.name, self.uuid)

class Stream(models.Model):
    # TODO: Consider cascade model for stream deletion
    stream = models.ForeignKey(Client, on_delete=models.CASCADE)

class Card(models.Model):
    uuid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    title = models.CharField(max_length=256)
    src = models.URLField()
    img = models.URLField()
    streams = models.ManyToManyField(Stream)

    def __str__(self):
        return "{0} ({1}), <{2}>".format(
                Truncator(self.title).chars(40),
                Truncator(self.src).chars(40),
                self.uuid)

class Decision(models.Model):
    card = models.ForeignKey(Card, on_delete=models.CASCADE)
    value = models.IntegerField()

class Flag(models.Model):
    title = models.CharField(max_length=256)
    cards = models.ManyToManyField(Card);

class Tag(models.Model):
    title = models.CharField(max_length=256)
    cards = models.ManyToManyField(Card)

# A Client has many Stream, a Stream belongs to one Client
# A Stream has many Cards, a Card belongs to many Streams
# A Client has many Decisions, a Decision belongs to one Client
# A Decision has one Card, a Card has many Decisions

# A Card has many Flags, a Flag has many Cards
# A Card has many Tags, a Tag has many Cards
