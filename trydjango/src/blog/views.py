from django.shortcuts import render, get_object_or_404

# Create your views here.

from django.views.generic import (
    CreateView, 
    DetailView,
    ListView,
    UpdateView,
    ListView,
    DeleteView
)
from .models import Article

class ArticleListView(ListView):
    template_name = 'articles/article_list.html'
    queryset = Article.objects.all()    

    def get_object(self):
        id_ = self.kwargs.get("id")
        return get_object_or_404(Article, id=id_)

