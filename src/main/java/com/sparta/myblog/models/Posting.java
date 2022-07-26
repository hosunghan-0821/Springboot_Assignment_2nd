package com.sparta.myblog.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Posting extends Timestamped {

    //
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column
    private String contents;

    @Column
    private String image;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String password;

    public Posting(PostingRequestDto requestDto){
        this.password= requestDto.getPassword();
        this.writer= requestDto.getWriter();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
    public void update(PostingRequestDto requestDto){
        this.password = requestDto.getPassword();
        this.writer= requestDto.getWriter();
        this.title= requestDto.getTitle();
        this.contents= requestDto.getContents();
    }

}
