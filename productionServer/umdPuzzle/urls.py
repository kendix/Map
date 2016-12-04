from django.conf.urls import url

from . import views
app_name = 'umdPuzzle'

urlpatterns = [
    url(r'^getPlacePic/', views.getPlacePic, name='getPlacePic'),
    url(r'^postPuzzleSolved/$', views.postPuzzleSolved, name='postPuzzleSolved'),
    url(r'^getPlaces/$', views.getCompletedPuzzles, name='getCompletedPuzzles'),
]