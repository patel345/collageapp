package com.collageapp.controller;

import com.collageapp.payload.PostDto;
import com.collageapp.payload.PostResponse;
import com.collageapp.service.impl.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
    @RequestMapping("/api/posts")
    public class PostController {
        private PostService postService;

        public PostController(PostService postService) {

            this.postService = postService;
        }
        //create databade
        //http://localhost:8081/api/posts
    //@preAuthorize annotation use of spring security
    @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        //@valid use of spring validation
        //bindingResult use of spring validation
        public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
            //use of spring validation
            if(result.hasErrors()){
                return  new ResponseEntity<Object>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
            PostDto dto=postService.createPost(postDto);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
        //create all id no  in get method showing
        //http://localhost:8081/api/posts?pageNo=0&pageSize=5&sortBy=collageName&sortDir=asc
        @GetMapping
        public PostResponse getAllPosts(@RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
                                        @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                        @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
                                        @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){

           return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

        }
        //create write id no in showing in post
    //http://localhost:8081/api/posts/1

    @GetMapping("/{id}")
        public ResponseEntity<PostDto>getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);
        return ResponseEntity.ok(dto);
    }
    //update post by id rest api
    //http://localhost:8081/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="id")long id){
        PostDto  postResponse=postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    //delete post rest api//http://localhost:8081/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable(name="id")long id){
            postService.deletePostById(id);
            return new ResponseEntity<>("Post entity delete successfully",HttpStatus.OK);
    }

    }

