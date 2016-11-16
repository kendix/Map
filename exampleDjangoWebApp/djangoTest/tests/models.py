from __future__ import unicode_literals

from django.db import models
from django.utils.encoding import python_2_unicode_compatible

# Create your models here.

class Puzzle(models.Model):
    """ 
        This defines a puzzle's picture (located as a static file, stored as a string),
    	the puzzle's coordinates, whether the puzzle is completed, 
    	and everyone who has solved the puzzle 
    """
    latitude = models.FloatField(null=False, blank=False, help_text="upper most latitude")
    longitude = models.FloatField(null=False, blank=False, help_text="left most longitude")
    picture = models.CharField(max_length=100, unique=True) # only one puzzle can have the same picture url
    completed = models.BooleanField(default=False)
    # solvers will be stored as a string delimited by ||
    # ex: 'user1'||'user2'||'user3'
    solvers = models.TextField()


