package br.com.apirest.leadersofts.leadcapture.application;

//import br.com.leadersofts.LeadersoftsCloudDriven.core.cases.EmailSenderUseCase;
//import br.com.leadersofts.LeadersoftsCloudDriven.infrastructure.aws.ses.SesEmailSender;
import br.com.apirest.leadersofts.leadcapture.core.EmailSenderUseCase;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderUseCaseImpl implements EmailSenderUseCase {

//    private SesEmailSender emailSender;
//
//    public EmailSenderUseCaseImpl(SesEmailSender emailSenderAdapter) {
//        this.emailSender = emailSenderAdapter;
//    }

    @Override
    public void sendEmail(String to, String subject, String body) {
//        this.emailSender.sendMail(to,subject,body);
    }

}
