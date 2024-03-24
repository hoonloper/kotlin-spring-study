package com.example.auth.member.controller

import com.example.auth.common.dto.BaseResponse
import com.example.auth.member.dto.MemberDtoRequest
import com.example.auth.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController (
    private val memberService: MemberService
) {
    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)

        return BaseResponse(message = resultMsg)
    }
}