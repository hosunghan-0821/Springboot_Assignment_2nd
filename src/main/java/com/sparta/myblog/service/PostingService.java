package com.sparta.myblog.service;

import com.sparta.myblog.models.Posting;
import com.sparta.myblog.models.PostingRepository;
import com.sparta.myblog.models.PostingRequestDto;
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
    public boolean  updatePosting(Long id, PostingRequestDto requestDto){
        //id 를 통해서 해당하는 정보 DB로부터 가져오기
        Posting posting = postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디가 없습니다."));
        //데이터 업데이트
        posting.update(requestDto);
        return true;
    }


}
