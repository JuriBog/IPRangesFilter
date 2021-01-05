package aws.ipranges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
@EnableCaching
public class IpRangesApplication {

    @Bean
    public WebClient getWebClient(){
        return WebClient.builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().maxInMemorySize(2* 1024 * 1024);
                })
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(IpRangesApplication.class, args);

    }




}
