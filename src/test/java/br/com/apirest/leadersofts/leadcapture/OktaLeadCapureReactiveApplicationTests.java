package br.com.apirest.leadersofts.leadcapture;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class OktaLeadCapureReactiveApplicationTests {

	@Test
	void contextLoads() {
	}

	//    @Value("${aws_access_key}")
//    private String accessKey;
//
//    @Value("${aws_secret_key}")
//    private String secretKey;

//    @Value("${aws.accessKeyId}")
//	private String accessKey;
//
//	@Value("${aws.secretKeyId}")
//	private String secretKey;
//
//    @Value("${aws.sns.topic.okta.arn}")
//    private String awsTopicArn;

	@InjectMocks
	@Mock
	private Environment env;

//    @Test
//    public void shouldGetEnvCredentials() {
//        System.out.println("Access Key looks like: " + accessKey);
//        System.out.println("Secret Key maybe... " + secretKey);
//        String path = env.getProperty("aws.sns.topic.okta.arn.path");
//        var path2 = env.getProperty("aws.accessKeyId");
//    }

}
