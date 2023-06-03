package com.collageapp.payload;

import com.collageapp.entity.Comment;
import com.collageapp.entity.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private  long id;
    //@NotEmpty use of spring validation
    @NotEmpty
    @Size(min=5, message = "Name should be at least 5 characters")
    private String name;
    @NotEmpty
    @Size(min=5, message = "Name should be at least 5 characters")
    private String email;
    @NotEmpty
    @Size(min=5, message = "Name should be at least 5 characters")
    private  String body;
    //private Post post;
    //private Comment comment;
}
