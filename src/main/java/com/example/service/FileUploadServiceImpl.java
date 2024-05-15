package com.example.service;

import com.example.model.FileUpload;
import com.example.repository.FileUploadRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final FileUploadRepository fileUploadRepository;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void uploadFile(FileUpload fileUpload) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(fileUpload.getEmail());
            mimeMessageHelper.setSubject("Project Overview");
            mimeMessageHelper.setText("Dear: " + fileUpload.getUsername() + ","
                    + "\n" + "Please find the project overview file in the attachment below:" + "\n"
                    + "Let us know if you have any queries related to the project" + "\n" + "Thank You");

            FileSystemResource resource = new FileSystemResource("D://java//frontend//css.pdf");

            mimeMessageHelper.addAttachment(Objects.requireNonNull(resource.getFilename()), resource);

            fileUploadRepository.save(fileUpload);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
