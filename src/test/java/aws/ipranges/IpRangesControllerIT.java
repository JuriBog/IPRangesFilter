package aws.ipranges;


import aws.ipranges.controller.IpRangesController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "36000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IpRangesControllerIT {


    @Autowired
    private WebTestClient webTestClient;

     @Test
     public void DifferentRegionsShouldNotMatch(){
     String usPrefixes = webTestClient.get()
             .uri("/ip-ranges?region=US")
             .exchange()
             .expectStatus()
             .isOk()
             .expectHeader()
             .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
             .expectBody(String.class)
             .returnResult()
             .getResponseBody();

     String euPrefixes = webTestClient
             .get()
             .uri("/ip-ranges?region=EU")
             .exchange()
             .expectStatus()
             .isOk()
             .expectHeader()
             .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
             .expectBody(String.class)
             .returnResult()
             .getResponseBody();

     Assertions.assertNotEquals(euPrefixes, usPrefixes);
     }

     @Test
     public void shouldReturnMessageForInvalidRegion(){
     String message = "No IP-Prefixes for the given Region:KK";
     webTestClient
             .get()
             .uri("/ip-ranges?region=KK")
             .exchange()
             .expectStatus()
             .isOk()
             .expectHeader()
             .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
             .expectBody(String.class)
             .isEqualTo(message);
     }
}
