package br.com.apirest.leadersofts.leadcapture.infrastructure.cloud.aws.config.sqs;

import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsConfiguration {

    private static final String ENDPOINT_URL = "http://localhost:4566";
    private static final Region DEFAULT_REGION = Region.US_EAST_1;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKeyId}")
    private String secretKey;

    @PostConstruct
    public void setSystemProperty() {
        SystemPropertiesCredentialsProvider systemPropertiesCredentialsProvider = new SystemPropertiesCredentialsProvider();
        System.setProperty("aws.accessKeyId",accessKey);
        System.setProperty("aws.secretAccessKey",secretKey);
    }

    @Bean
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    @Bean
    SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .credentialsProvider(SystemPropertyCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(ENDPOINT_URL))
                .build();
    }

    @Bean
    public SqsAsyncClient amazonSQSAsyncClient() {
        return SqsAsyncClient
                .builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .applyMutation(builder -> {
                    builder.endpointOverride(URI.create(ENDPOINT_URL));
                })
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configure(o -> o.queueNotFoundStrategy(QueueNotFoundStrategy.FAIL))
                .build();
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(DEFAULT_REGION)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .applyMutation(builder -> {
                    builder.endpointOverride(URI.create(ENDPOINT_URL));
                })
                .build();
    }

//    @PostConstruct
//    @Bean
//    @Primary
//    public AWSCredentialsProvider buildDefaultAWSCredentialsProvider() {
//        return new DefaultAWSCredentialsProviderChain();
//    }

}
