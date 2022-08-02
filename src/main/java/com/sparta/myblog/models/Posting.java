package com.sparta.myblog.models;

import com.sparta.myblog.Dto.PostingRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
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

    @Column(nullable = true)
    private String password;

    @OneToMany(mappedBy = "posting",fetch = FetchType.EAGER,cascade =CascadeType.ALL)// mappedBy 연관관계의 주인이 아니다 (난 FK가 아니다 DB에 칼럼을 만들지 말아라)
    private List<Comment> comment;

    public Posting(UserDetailsImpl userDetails, PostingRequestDto requestDto){
        this.writer= userDetails.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
    public void update(PostingRequestDto requestDto){
        this.title= requestDto.getTitle();
        this.contents= requestDto.getContents();
    }

}
