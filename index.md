---
layout: page
title: 首页
tagline: by 何通庆
---
{% include JB/setup %}
    
<h2>ppt</h2>
<ul>
{% assign pages_list = site.pages %}
{% assign group = 'ppt' %}
{% include JB/pages_list %}
</ul>

## 所有文章
<ul class="posts">
  {% for post in site.posts %}
    <li><span>{{ post.date | date:"%Y-%m-%d" }}</span> &raquo; <a href="{{ BASE_PATH }}{{ post.url }}">{{ post.title }}</a></li>
  {% endfor %}
</ul>

