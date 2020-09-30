package com.posts.web;

import com.posts.service.posts.PostsService;
import com.posts.web.dto.PostsResponseDto;
import com.posts.web.dto.PostsSaveRequestDto;
import com.posts.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping
    public Long save(@RequestBody PostsSaveRequestDto
                                            requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody
        PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }


}
