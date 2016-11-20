from django.conf.urls import url

from . import views
app_name = 'umdPuzzle'

urlpatterns = [
    url(r'^$', views.index, name='index'),
    #url(r'^getPlace/(?P<lat>[+-]*([0-9]*[.])?[0-9]+)/(?P<lon>[+-]*([0-9]*[.])?[0-9]+)$', views.getPlace, name='getPlace'),
    url(r'^getPlace/', views.getPlace, name='getPlace'),
    url(r'^getPlacePic/(?P<place_id>[0-9]+)/$', views.getPlacePic, name='getPlacePic'),
    url(r'^postPuzzleSolved/$', views.postPuzzleSolved, name='postPuzzleSolved'),
    url(r'^getCompletedPuzzles/$', views.getCompletedPuzzles, name='getCompletedPuzzles'),
]