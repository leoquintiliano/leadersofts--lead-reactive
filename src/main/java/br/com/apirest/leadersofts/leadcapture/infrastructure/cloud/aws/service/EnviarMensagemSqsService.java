package br.com.apirest.leadersofts.leadcapture.infrastructure.cloud.aws.service;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

@Service
public class EnviarMensagemSqsService {

    public SendMessageRequest popularBodyDaMensagem(Object mensagem, String nomeFilaSQS) {
        var msgBody = popularBodyDaMensagem(mensagem);
        var sendMessagQue = prepareSendMessageRequestStandard(msgBody, nomeFilaSQS);
        return sendMessagQue;
    }

    private static String popularBodyDaMensagem(Object o) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Erro ao construir body da mensagem a partir de objeto: "
                            .concat(o.getClass().getName())
                            .concat(". Exception: ")
                            .concat(e.getMessage())
            );
        }
    }

    private static SendMessageRequest prepareSendMessageRequestStandard(String queueUrl, String msgBody) {
        return new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(msgBody);
    }

}
