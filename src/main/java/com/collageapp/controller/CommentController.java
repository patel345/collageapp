package com.collageapp.controller;

import com.collageapp.payload.CommentDto;
import com.collageapp.service.impl.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/")
public class CommentController {
  private CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }
    //http://localhost:8081/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object>createComment(@Valid @PathVariable("postId") long postId, @RequestBody CommentDto commentDto, BindingResult result){
        if(result.hasErrors()){
            return  new ResponseEntity<Object>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentDto  dto=commentService.createComment(postId,commentDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8081/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto>getCommentsByPostId(
            @PathVariable("postId") long postId
    ){
      return commentService.getCommentsByPostId(postId) ;
    }
    //http://localhost:8081/api/posts/1/comments/1
  @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>getCommentById(
            @PathVariable("postId")  long postId,
            @PathVariable("id") long commentId
    ){
     CommentDto dto= commentService.getCommentById(postId,commentId) ;
      return  new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8081/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateComment(
            @PathVariable("postId")long postId,
            @PathVariable("id") long id,
            @RequestBody CommentDto commentDto
    ){
       CommentDto dto= commentService.updateComment(postId, id,commentDto);
       return  new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String>deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id
    ){
        commentService.deleteComment(postId,id);
       return  new ResponseEntity<>("Comment is deleted",HttpStatus.OK) ;
    }
}
