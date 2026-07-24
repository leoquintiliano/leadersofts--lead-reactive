package br.com.apirest.leadersofts.leadcapture.adapter;

public interface EmailSenderAdapter {

    void sendMail(String to, String subject, String body);

}
