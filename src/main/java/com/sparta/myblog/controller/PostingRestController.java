package com.sparta.myblog.controller;


import com.sparta.myblog.models.Posting;
import com.sparta.myblog.repository.PostingRepository;
import com.sparta.myblog.Dto.PostingRequestDto;
import com.sparta.myblog.Dto.PostingResponseDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.PostingService;
import com.sparta.myblog.utils.PostingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostingRestController {

    private final PostingRepository postingRepository;
    private final PostingService postingService;
    private final PostingUtils postingUtils;

    //게시글 전체 조회
    @GetMapping("/api/blogs")
    public List<PostingResponseDto> readPostings(){
        List<Posting> postings = postingRepository.findAllByOrderByCreatedAtDesc();

        return postingUtils.changeToPostingVoAPI(postings);
    }
    //게시글 상세 조회
    @GetMapping("/api/blogs/details")
    public PostingResponseDto readPosting(@RequestParam Long id){
        Posting posting = postingService.readPosting(id);
        return new PostingResponseDto(posting);
    }

    //게시글 작성
    //TO-DO 게시글 작성 변경하기
    @PostMapping("/api/blogs")
    public PostingResponseDto writePosting(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody PostingRequestDto requestDto){
        Posting posting = postingService.writePosting(userDetails, requestDto);

        return new PostingResponseDto(posting);
    }

    //게시글 업데이트
    @PutMapping("/api/blogs/{id}")
    public PostingResponseDto updatePosting(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id , @RequestBody PostingRequestDto requestDto){

        Posting posting = postingService.updatePosting(id,requestDto,userDetails);
        return new PostingResponseDto(posting);
    }

    //게시글 삭제하기
    @DeleteMapping("/api/blogs/{id}")
    public PostingResponseDto deletePosting(@PathVariable Long id ,@AuthenticationPrincipal UserDetailsImpl userDetails){

        boolean isDelete = postingService.deletePosting(id,userDetails);
        return new PostingResponseDto(isDelete);
    }

    //게시글 비밀번호 확인하기
    @PostMapping("/api/blogs/passwords/{id}")
    public boolean checkPassword(@PathVariable Long id,@RequestBody PostingRequestDto requestDto){
        Posting posting = postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디 없습니다"));
        return postingUtils.checkPassword(posting,requestDto);
    }
}
