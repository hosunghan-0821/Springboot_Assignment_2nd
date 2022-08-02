package com.sparta.myblog.service;

import com.sparta.myblog.models.Posting;
import com.sparta.myblog.repository.PostingRepository;
import com.sparta.myblog.Dto.PostingRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.utils.PostingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final PostingUtils postingUtils;

    @Transactional
    public boolean  updatePosting(Long id, PostingRequestDto requestDto, UserDetailsImpl userDetails){
        //id 를 통해서 해당하는 정보 DB로부터 가져오기
        Posting posting = postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디가 없습니다."));
        //작성자와 요청 보낸 사람의 id가 같은지 확인하기
        if(userDetails.getUsername().equals(posting.getWriter())){
            //데이터 업데이트
            posting.update(requestDto);
            return true;
        }
        else{
            return false;
        }
    }

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
