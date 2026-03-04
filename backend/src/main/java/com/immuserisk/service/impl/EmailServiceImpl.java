package com.immuserisk.service.impl;

import com.immuserisk.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件服务实现类
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String from;
    
    @Value("${spring.mail.nickname:社区便民服务平台}")
    private String nickname;
    
    @Override
    public boolean sendSimpleMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(nickname + "<" + from + ">");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            logger.info("简单邮件已发送至: {}", to);
            return true;
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常: ", e);
            return false;
        }
    }
    
    @Override
    public boolean sendHtmlMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(nickname + "<" + from + ">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            
            mailSender.send(message);
            logger.info("HTML邮件已发送至: {}", to);
            return true;
        } catch (MessagingException e) {
            logger.error("发送HTML邮件时发生异常: ", e);
            return false;
        }
    }
    
    @Override
    public boolean sendVerificationCode(String to, String code) {
        String subject = "社区便民服务平台 - 邮箱验证码";
        
        // 构建HTML格式的邮件内容
        String content = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #eaeaea; border-radius: 5px; background-color: #f9f9f9;\">"
                + "<h2 style=\"color: #333; text-align: center;\">邮箱验证码</h2>"
                + "<p style=\"color: #666; font-size: 16px;\">尊敬的用户，您好：</p>"
                + "<p style=\"color: #666; font-size: 16px;\">您正在进行社区便民服务平台的账号注册，您的验证码为：</p>"
                + "<p style=\"text-align: center; font-size: 24px; font-weight: bold; color: #409EFF; background-color: #ecf5ff; padding: 10px; border-radius: 4px; letter-spacing: 5px;\">" 
                + code 
                + "</p>"
                + "<p style=\"color: #666; font-size: 14px;\">验证码有效期为10分钟，请勿将验证码泄露给他人。</p>"
                + "<p style=\"color: #666; font-size: 14px; margin-top: 30px;\">如果您没有进行相关操作，请忽略此邮件。</p>"
                + "<div style=\"margin-top: 40px; padding-top: 20px; border-top: 1px solid #eaeaea; color: #999; font-size: 12px; text-align: center;\">"
                + "<p>此邮件由系统自动发送，请勿直接回复</p>"
                + "<p>© " + java.time.Year.now().getValue() + " 社区便民服务平台</p>"
                + "</div>"
                + "</div>";
        
        return sendHtmlMail(to, subject, content);
    }
} 