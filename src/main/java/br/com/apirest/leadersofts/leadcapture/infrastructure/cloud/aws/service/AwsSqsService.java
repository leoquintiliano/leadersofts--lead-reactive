package br.com.apirest.leadersofts.leadcapture.infrastructure.cloud.aws.service;

import com.amazonaws.services.sqs.AmazonSQS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.function.Consumer;

@Service
public class AwsSqsService {

    private final EnviarMensagemSqsService enviarMensagemSqsService;

    @Value("${sqs.queue.okta}")
    private String queueName;

    private final AmazonSQS amazonSQS;

    private final SqsClient sqsClient;

    public AwsSqsService(EnviarMensagemSqsService enviarMensagemSqsService, AmazonSQS amazonSQS, SqsClient sqsClient) {
        this.enviarMensagemSqsService = enviarMensagemSqsService;
        this.amazonSQS = amazonSQS;
        this.sqsClient = sqsClient;
    }

    public void postarNaFila(String payload) {

        var messageBody = this.enviarMensagemSqsService.popularBodyDaMensagem(payload,queueName);
//        sqsClient.sendMessage((Consumer<SendMessageRequest.Builder>) messageBody);
        amazonSQS.sendMessage(messageBody).getMessageId();

    }

}
