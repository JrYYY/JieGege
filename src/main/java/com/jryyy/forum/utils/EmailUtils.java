package com.jryyy.forum.utils;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.List;

/**
 * @author JrYYY
 */
@Component
public class EmailUtils {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;


    /**
     * 不带附件放送email
     * @param to        发送到
     * @param title     标题
     * @param content   内容
     * @throws Exception 异常
     */
    public void sendSimpleMail(String to, String title, String content) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     *  带附件放送email
     * @param to            发送到
     * @param title         标题
     * @param content       内容
     * @param fileList      附件列表
     * @throws Exception
     */
    public void sendAttachmentsMail(String to, String title, String content, List<File> fileList) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content);
            String fileName;
            for (File file : fileList) {
                fileName = MimeUtility.encodeText(file.getName(), "GB2312", "B");
                helper.addAttachment(fileName, file);
            }
            mailSender.send(message);
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.mailDeliveryFailed);
        }
    }

}

