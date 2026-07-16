package br.com.apirest.leadersofts.leadcapture.core;

public interface EmailSenderUseCase {
    void sendEmail(String to, String subject, String body);
}
