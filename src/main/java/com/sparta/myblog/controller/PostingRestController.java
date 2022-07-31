package com.sparta.myblog.controller;


import com.sparta.myblog.models.Posting;
import com.sparta.myblog.repository.PostingRepository;
import com.sparta.myblog.Dto.PostingRequestDto;
import com.sparta.myblog.Dto.PostingResponseDto;
import com.sparta.myblog.service.PostingService;
import com.sparta.myblog.utils.PostingUtils;
import lombok.RequiredArgsConstructor;
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
    public Posting readPosting(@RequestParam Long id){
        return postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디가 없습니다"));
    }

    //게시글 작성
    @PostMapping("/api/blogs")
    public Posting writePosting(@RequestBody PostingRequestDto requestDto){
        Posting posting = new Posting(requestDto);
        return postingRepository.save(posting);
    }

    //게시글 업데이트
    @PutMapping("/api/blogs/{id}")
    public boolean updatePosting(@PathVariable Long id , @RequestBody PostingRequestDto requestDto){

        return postingService.updatePosting(id,requestDto);
    }

    //게시글 삭제하기
    @DeleteMapping("/api/blogs/{id}")
    public boolean deletePosting(@PathVariable Long id ,@RequestBody PostingRequestDto requestDto){

        Posting posting =  postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당하는 아이디가 없습니다"));
        if(postingUtils.checkPassword(posting,requestDto)){
            postingRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    //게시글 비밀번호 확인하기
    @PostMapping("/api/blogs/passwords/{id}")
    public boolean checkPassword(@PathVariable Long id,@RequestBody PostingRequestDto requestDto){
        Posting posting = postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디 없습니다"));
        return postingUtils.checkPassword(posting,requestDto);
    }
}
