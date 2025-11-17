package com.likelion.likelionmail.mail.application;

import com.likelion.likelionmail.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailScheduler {

    private final MailService mailService;

    @Value("${spring.mail.scheduled.target-email}")
    private String targetEmail;

    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDailyMail() {
        String subject = "[자동발송] 매일 00시 정기 메일";
        String body = """
              <div style="font-family: Arial, sans-serif; padding: 20px;">
                  <h2>안녕하세요 🦁</h2>
                  <p>이 메일은 매일 00시에 자동으로 발송되는 메일입니다.</p>
                  <p style="color: gray;">발송 시각: %s</p>
              </div>
              """.formatted(java.time.LocalDateTime.now());

        try {
            mailService.sendTestMail(targetEmail);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        }
    }
}
