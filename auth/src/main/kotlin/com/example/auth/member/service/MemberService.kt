package com.example.auth.member.service

import com.example.auth.common.exception.InvalidInputException
import com.example.auth.member.dto.MemberDtoRequest
import com.example.auth.member.entity.Member
import com.example.auth.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberService (
    private val memberRepository: MemberRepository
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)

        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID입니다.")
        }

        member = memberDtoRequest.toEntity()

        memberRepository.save(member)

        return "회원가입이 완료되었습니다."
    }
}