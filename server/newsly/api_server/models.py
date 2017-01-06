from django.db import models

class Client(models.model):
    uuid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=256)

class Stream(models.model):
    # TODO: Consider cascade model for stream deletion
    stream = models.ForeignKey(Stream, on_delete=models.CASCADE)

class Card(model.model):
    uuid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    title = model.CharField(max_length=256)
    src = model.URLField()
    img = model.URLField()
    streams = models.ManyToManyField(Stream)

class Decision(model.model):
    card = models.ForeignKey(Card, on_delete=models.CASCADE)
    value = models.IntegerField()

class Flag(model.model):
    title = model.CharField(max_length=256)
    cards = models.ManyToManyField(Card);

class Tag(model.model):
    title = model.CharField(max_length=256)
    cards = models.ManyToManyField(Card)

# A Client has many Stream, a Stream belongs to one Client
# A Stream has many Cards, a Card belongs to many Streams
# A Client has many Decisions, a Decision belongs to one Client
# A Decision has one Card, a Card has many Decisions

# A Card has many Flags, a Flag has many Cards
# A Card has many Tags, a Tag has many Cards
