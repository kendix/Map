from __future__ import unicode_literals

from django.db import models
from django.utils.encoding import python_2_unicode_compatible
from math import sin, cos, sqrt, atan2, radians

# Create your models here.

@python_2_unicode_compatible
class Place(models.Model):
    """ 
    This defines a puzzle's picture 
    (located as a static file, stored as a string),
    the puzzle's coordinates, whether the puzzle is completed, 
    and everyone who has solved the puzzle 
    """

    name = models.CharField(max_length=100, unique=True,
        help_text="name of the location/puzzle")
    latitude = models.FloatField(null=False, blank=False,
        help_text="upper most latitude")
    longitude = models.FloatField(null=False, blank=False, 
        help_text="left most longitude")
    # only one puzzle can have the same picture url
    picture = models.CharField(max_length=100, unique=False, 
        help_text="name of picture") 

    completed = models.BooleanField(default=False, 
        help_text="True if puzzle is completed, false if incomplete")

    # solvers will be stored as a string delimited by ||
    # ex: 'user1'||'user2'||'user3'
    solvers = models.TextField(null=True, blank=True, help_text="users who have solved the puzzle")

    def __str__(self):
        return self.name

