package br.com.apirest.leadersofts.leadcapture.infrastructure.listener;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.MessageRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class OktaListener {

    private final Gson gson = new Gson();

//    private final EmailSenderUseCase emailSenderUseCase;

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper().findAndRegisterModules();

//    @Value("${ses.source.mail}")
//    private String email;

//    public OktaSQSListener(EmailSenderUseCase emailSenderUseCase) {
//        this.emailSenderUseCase = emailSenderUseCase;
//    }

    @SqsListener(value = "${sqs.queue.okta}")
    public void printReceivedMessagesLog(String messageContent) {
        try {
            var mensagem = gson.fromJson(messageContent, MessageRecord.class);

//            emailSenderUseCase.sendEmail(email, "Teste" , "Nova mensagem : " + mensagem);
            log.info("Message received {}", messageContent);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


//    @SqsListener
//    public SqsAsyncClient sqsAsyncClient() {
//        return SqsAsyncClient.builder()
//                .endpointOverride(URI.create("http://localhost:4566"))
//                .region(Region.SA_EAST_1)
//                .build();
//    }

}
