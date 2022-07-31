package com.sparta.myblog.Dto;

import com.sparta.myblog.models.Posting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class PostingResponseDto {
    private String title;
    private String contents;
    private String writer;
    private String password;
    private Long id;
    private String createdAt;

    //posting to PostingVo
    public PostingResponseDto(Posting posting , String date){

        this.title= posting.getTitle();
        this.contents=posting.getContents();
        this.writer= posting.getWriter();
        this.password= posting.getPassword();
        this.id= posting.getId();

        //작성날짜,오늘날짜 비교해서 나타내기
        try{
            String[] posting_date = posting.getCreatedAt().split(" ");
            if(posting_date[0].equals(date)){
                this.createdAt= posting_date[1].substring(0,5);
            }
            else{
                this.createdAt=posting_date[0];
            }
        }catch (Exception e){
            this.createdAt= posting.getCreatedAt();
            e.printStackTrace();
        }

    }
    public PostingResponseDto(Posting posting){
        this.title = posting.getTitle();
        this.writer = posting.getWriter();
        this.createdAt= posting.getCreatedAt();
    }

}
