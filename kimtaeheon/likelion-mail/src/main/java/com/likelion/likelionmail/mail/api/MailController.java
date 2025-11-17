package com.likelion.likelionmail.mail.api;

import com.likelion.likelionmail.mail.api.dto.request.MailRequest;
import com.likelion.likelionmail.mail.api.dto.response.MailResponse;
import com.likelion.likelionmail.mail.application.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    // DTO 검증 기반 이메일 전송
    @PostMapping("/mail/send")
    public MailResponse sendTestMail(@Valid @RequestBody MailRequest request) {

        mailService.sendTestMail(request.getTo());

        return new MailResponse("메일 전송 완료!");
    }

    // 로그인한 사용자 이메일로 전송
    @GetMapping("/mail/send-login-user")
    public MailResponse sendMailToLoginUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("로그인 정보가 없습니다.");
        }

        String email = authentication.getName();
        mailService.sendTestMail(email);

        return new MailResponse("로그인 유저 메일 전송 완료!");
    }
}
