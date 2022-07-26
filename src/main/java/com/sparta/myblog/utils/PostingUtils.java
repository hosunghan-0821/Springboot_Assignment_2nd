package com.sparta.myblog.utils;

import com.sparta.myblog.models.Posting;
import com.sparta.myblog.models.PostingRequestDto;
import com.sparta.myblog.models.PostingVo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<PostingVo> changeToPostingVo(List<Posting> postings) {
        List<PostingVo> postingVoList = new ArrayList<>();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (Posting posting : postings) {
            PostingVo postingVo = new PostingVo(posting,date);
            postingVoList.add(postingVo);
        }

        return postingVoList;
    }

    public List<PostingVo> changeToPostingVoAPI(List<Posting> postings){
        List<PostingVo> postingVoList = new ArrayList<>();
        for(Posting posting: postings){
            PostingVo postingVo= new PostingVo(posting);
            postingVoList.add(postingVo);
        }
        return postingVoList;
    }


}
