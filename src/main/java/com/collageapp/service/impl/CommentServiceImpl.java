package com.collageapp.service.impl;

import com.collageapp.entity.Comment;
import com.collageapp.entity.Post;
import com.collageapp.exception.CollageApiException;
import com.collageapp.exception.ResourceNotFoundException;
import com.collageapp.payload.CommentDto;
import com.collageapp.repository.CommentRepository;
import com.collageapp.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    private PostRepository postRepository;
private CommentRepository commentRepository;
private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
      Comment comment= mapToEntity(commentDto);
     Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
     comment.setPost(post);
      Comment newComment=commentRepository.save(comment);
      CommentDto dto=mapToDto(newComment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
       Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
      return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

            Post post= postRepository.findById(postId).orElseThrow(
                    ()->new ResourceNotFoundException("post","id",postId));


            Comment comment=commentRepository.findById(commentId).orElseThrow(
                    ()->new ResourceNotFoundException("Comment","id",commentId));
            if(comment.getPost().getId()!=post.getId()){
                throw  new CollageApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
            }
           return mapToDto(comment);

        }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("comment","id",id));
        if(comment.getPost().getId()!=post.getId()) {
            throw new CollageApiException(HttpStatus.BAD_REQUEST, "Post not matching with comment");
        }
        comment.setId(id);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
     Comment newComment=commentRepository.save(comment);
            return mapToDto(newComment);

    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("comment","id",id));
        if(comment.getPost().getId()!=post.getId()) {
            throw new CollageApiException(HttpStatus.BAD_REQUEST, "Post not matching with comment");
        }
        commentRepository.deleteById(id);

    }


    private CommentDto mapToDto(Comment newComment) {

        CommentDto commentDto=modelMapper.map(newComment,CommentDto.class);
        // CommentDto commentDto=new CommentDto();
       // commentDto.setId(newComment.getId());
       // commentDto.setName(newComment.getName());
       // commentDto.setEmail(newComment.getEmail());
       /// commentDto.setBody(newComment.getBody());
        //commentDto.setPost(newComment.getPost());
       return  commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment=modelMapper.map(commentDto,Comment.class);
       // Comment comment=new Comment();
        //comment.setId(commentDto.getId());
       // comment.setName(commentDto.getName());
       // comment.setEmail(commentDto.getEmail());
       // comment.setBody(commentDto.getBody());
        //comment.setPost(commentDto.getPost());
        return comment;
    }
}
