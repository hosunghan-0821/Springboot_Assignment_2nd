package com.sparta.myblog.controller;

import com.sparta.myblog.models.Posting;
import com.sparta.myblog.models.UserInfo;
import com.sparta.myblog.repository.PostingRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.utils.PostingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PostingRepository postingRepository;
    private final PostingUtils postingUtils;



    @GetMapping("/")
    public String main(Model model){
        List<Posting> postings = postingRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("postingList", postingUtils.changeToPostingVo(postings));
        //PostingVo postingVo = new PostingVo()
        return "index";
    }

    @GetMapping("/posting")
    public String writePage(@AuthenticationPrincipal UserDetailsImpl userDetails ,Model model){
        model.addAttribute("nickname",userDetails.getUser().getNickname());
        model.addAttribute("posting","");
        return "write";
    }
    @GetMapping("/posting/modified")
    public String updatePage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model, @RequestParam Long id){
        Posting posting = postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디가 없습니다"));
        model.addAttribute("nickname",userDetails.getUser().getNickname());
        model.addAttribute("posting",posting);
        return "write";
    }

    @GetMapping("/posting/details")
    public String detailPage(@AuthenticationPrincipal UserDetailsImpl userDetails,Model model, @RequestParam Long id){
        try{
            model.addAttribute("nickname",userDetails.getUser().getNickname());
        }catch (Exception e){

        }

        model.addAttribute("id",id);
        return "postRead";
    }

}
