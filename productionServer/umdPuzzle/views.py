from django.shortcuts import render, get_object_or_404, redirect
from django.http import HttpResponse, JsonResponse
import json
from models import Place

# Create your views here.
def index(request):
    example = {
        'solved' : 'false',
        'users_completed' : [
        ],
    }
    return HttpResponse(json.dumps(example), content_type='application/json')

def getCompletedPuzzles(request):
    place_qs = Place.objects.all()
    response = {}
    completedPuzzles = []
    for place in place_qs:
        temp_obj = {
                'name' : place.name,
                'lat' : place.latitude,
                'lng' : place.longitude,
                'pic' : place.picture,
                'completed' : place.completed,
        }
        completedPuzzles.append(temp_obj)
    response["buildings"] = completedPuzzles

    return JsonResponse(response)

def postPuzzleSolved(request):
    response_data = {}
    if request.method != "POST":
        response_data['status'] = "failed: not POST request"
        response_data['score'] = 0
        return JsonResponse(response_data)
    else:
        try:
            building_name = str(request.POST['name'])
        except Exception:
            response_data['status'] = "failed: invalid POST request"
            response_data['score'] = 0
            return JsonResponse
            
        get_place = get_object_or_404(Place, name=building_name)
        get_place.addSolver(username)
        get_place.completed = True
        get_place.save()
        score = get_place.getScore()


        response_data['status'] = "success"
        response_data['score'] = score

        return JsonResponse(response_data)


def getPlacePic(request):
    if request.GET.get('name'):
        picName = request.GET.get('name')
        context = {
            'picture' : "img/" + picName 
        }
        return render(request, 'umdPuzzle/pic.html', context)
    else:
        return HttpResponse("hello");
        # context = {
        #     'picture' : "img/dog.jpg" 
        # }
        # return render(request, 'umdPuzzle/pic.html', context)