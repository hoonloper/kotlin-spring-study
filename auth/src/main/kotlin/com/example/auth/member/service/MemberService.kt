package com.example.auth.member.service

import com.example.auth.common.exception.InvalidInputException
import com.example.auth.common.status.ROLE
import com.example.auth.member.dto.MemberDtoRequest
import com.example.auth.member.entity.Member
import com.example.auth.member.entity.MemberRole
import com.example.auth.member.repository.MemberRepository
import com.example.auth.member.repository.MemberRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
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

        val memberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }
}