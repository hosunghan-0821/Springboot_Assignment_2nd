package com.sparta.myblog.service;

import com.sparta.myblog.models.Posting;
import com.sparta.myblog.repository.PostingRepository;
import com.sparta.myblog.Dto.PostingRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.utils.PostingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final PostingUtils postingUtils;


    //게시글 상세읽기

    public Posting readPosting(Long id){
        Posting posting=postingRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글 없어요"));
        return posting;
    }

    //게시글 작성

    public Posting writePosting(@AuthenticationPrincipal UserDetailsImpl userDetails , PostingRequestDto requestDto){

        Posting posting = new Posting(userDetails,requestDto);
        return postingRepository.save(posting);
    }

    //수정하기
    @Transactional
    public Posting  updatePosting(Long id, PostingRequestDto requestDto, UserDetailsImpl userDetails){
        //id 를 통해서 해당하는 정보 DB로부터 가져오기
        Posting posting = postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디가 없습니다."));
        //작성자와 요청 보낸 사람의 id가 같은지 확인하기
        if(userDetails.getUsername().equals(posting.getWriter())){
            //데이터 업데이트
            posting.update(requestDto);
            return posting;
        }
        else{
            throw new IllegalArgumentException("게시글 작성자만 수정 가능합니다.");
        }
    }

    //삭제하기
    public boolean deletePosting(Long id, UserDetailsImpl userDetails){

        Posting posting =  postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당하는 아이디가 없습니다"));
        if(userDetails.getUsername().equals(posting.getWriter())){
            postingRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }


}
