package com.collageapp.service.impl;

import com.collageapp.payload.PostDto;
import com.collageapp.payload.PostResponse;

import java.util.List;

public interface PostService {


    PostDto  updatePost(PostDto postDto, long id) ;


    PostDto createPost(PostDto postDto);

   PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);

    void deletePostById(long id);
}
