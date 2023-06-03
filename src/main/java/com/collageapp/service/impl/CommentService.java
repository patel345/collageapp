package com.collageapp.service.impl;

import com.collageapp.entity.Comment;
import com.collageapp.payload.CommentDto;

import java.util.List;

public interface CommentService {

  CommentDto  createComment(long postId, CommentDto commentDto);
  List<CommentDto>getCommentsByPostId(long postId);


 CommentDto getCommentById(long postId, long commentId) ;

    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComment(long postId, long id);
}
