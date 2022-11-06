package com.commerce.user.controller;

import com.commerce.global.common.CommonResponse;
import com.commerce.user.dto.JoinMemberDto;
import com.commerce.user.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @PostMapping("/v1/member/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinMemberDto joinMemberDto) {
        memberServiceImpl.join(joinMemberDto);
        return CommonResponse.setResponse();
    }
}
