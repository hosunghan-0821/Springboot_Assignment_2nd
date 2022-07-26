package com.sparta.myblog.utils;

import com.sparta.myblog.models.Posting;
import com.sparta.myblog.models.PostingRequestDto;
import com.sparta.myblog.models.PostingResponseDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class PostingUtils {
    // 익명 게시글 비밀번호 확인 함수.
    public boolean checkPassword(Posting posting, PostingRequestDto requestDto) {
        if (requestDto.getPassword().equals(posting.getPassword())) return true;
        else return false;
    }

    //Posting to PostingVO
    public List<PostingResponseDto> changeToPostingVo(List<Posting> postings) {
        List<PostingResponseDto> postingVoList = new ArrayList<>();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (Posting posting : postings) {
            PostingResponseDto postingResponseDto = new PostingResponseDto(posting,date);
            postingVoList.add(postingResponseDto);
        }

        return postingVoList;
    }

    public List<PostingResponseDto> changeToPostingVoAPI(List<Posting> postings){
        List<PostingResponseDto> postingResponseDtoList = new ArrayList<>();
        for(Posting posting: postings){
            PostingResponseDto postingResponseDto = new PostingResponseDto(posting);
            postingResponseDtoList.add(postingResponseDto);
        }
        return postingResponseDtoList;
    }


}
