package br.com.apirest.leadersofts.leadcapture.infrastructure.cloud.aws.lambda.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import static java.util.Objects.isNull;

public class SNSEventHandler implements RequestHandler<SNSEvent,String> {

    @Override
    public String handleRequest(SNSEvent snsEvent, Context context) {
        if(isNull(snsEvent) || isNull(snsEvent.getRecords()) || snsEvent.getRecords().isEmpty()) {
            System.err.println("Received empty SNS event.");
            return "No records were found";
        }

        snsEvent.getRecords().forEach( record -> {
            System.out.println("SNSEventHandler:: Received SNS message: " + record.getSNS().getMessage());
        });
        return "Processed " + snsEvent.getRecords().size() + " messages";
    }

}
