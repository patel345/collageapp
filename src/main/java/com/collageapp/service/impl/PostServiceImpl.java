package com.collageapp.service.impl;

import com.collageapp.entity.Post;
import com.collageapp.exception.ResourceNotFoundException;
import com.collageapp.payload.PostDto;
import com.collageapp.payload.PostResponse;
import com.collageapp.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

   // private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository) {

        this.postRepository = postRepository;
        //this.mapper=mapper;
    }
//update
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        post.setCollageName(postDto.getCollageName());
        post.setCollageFee(postDto.getCollageFee());
        post.setCollageStream(postDto.getCollageStream());
        post.setCollageLocation(postDto.getCollageLocation());
        Post updatePost=postRepository.save(post);
        return  mapToDto((updatePost));
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert Dto to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        //convert entity to dto
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
        Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//second method apply ascending ans deccending using if /else
//Sort sort=null;
//if(sort.Dir.equalIgnorecase(sort.Direction.ASC.name()){
        //sort=Sort.by(sortBy).ascending
        //}else{
        //sort=sort.by(sortBy).descending();
   Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
   //page write a pageable
        Page<Post> posts = postRepository.findAll(pageable);
       List<Post> content=posts.getContent();
    //postResponse


    List<PostDto>postDtos= content.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public void deletePostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    //convert entity to dto
    private PostDto mapToDto(Post post) {
    //PostDto postDto=mapper.map(post,PostDto.class);
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setCollageName(post.getCollageName());
        postDto.setCollageFee(post.getCollageFee());
        postDto.setCollageStream(post.getCollageStream());
       postDto.setCollageLocation(post.getCollageLocation());
        return postDto;
    }

    //convert dto to entity
    private Post mapToEntity(PostDto postDto) {
     //Post post  = mapper.map(postDto,Post.class);
        Post post = new Post();
        post.setId(postDto.getId());
        post.setCollageName(postDto.getCollageName());
        post.setCollageFee(postDto.getCollageFee());
        post.setCollageStream(postDto.getCollageStream());
        post.setCollageLocation(postDto.getCollageLocation());
        return post;
    }
}

