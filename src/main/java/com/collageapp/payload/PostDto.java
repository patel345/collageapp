package com.collageapp.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
public class PostDto {
    private long id;
    //spring validation use of @Nootempty and @size
    @NotEmpty(message = "Is Mandatory")
    @Size(min=10,message = "Post collageName should  be at least 10 characters")
    private String collageName;
    @NotEmpty
    @Size(min=10,message = "Post collageFee should be at least 10 characters")
    private String collageFee;
    @NotEmpty
    @Size(min=10,message = "Post  collageStream should be at least 10 characters")
    private String collageStream;
    @NotEmpty
    @Size(min=10,message = "Post collageLocation should  be at least 10 characters")
    private String collageLocation;
}
