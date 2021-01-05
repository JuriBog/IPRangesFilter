package aws.ipranges;

import aws.ipranges.controller.IpRangesController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient(timeout = "36000")
@WebFluxTest(controllers = IpRangesController.class)
public class IpRangesControllerTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void shouldContainEuIpPrefixes() {
        String responseBody = webTestClient.get()
                .uri("/ip-ranges?region=EU")
                .exchange()
                .expectBody(String.class).returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/ip-ranges?region=EU")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                .expectBody(String.class)
                .isEqualTo( responseBody);

    }

    @Test
    public void DifferentRegionsShouldNotMatch(){
        String usPrefixes = webTestClient.get()
                .uri("/ip-ranges?region=US")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                .expectBody(String.class).returnResult().getResponseBody();

        String euPrefixes = webTestClient
                .get()
                .uri("/ip-ranges?region=EU")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                .expectBody(String.class).returnResult().getResponseBody();

        Assert.assertNotEquals(euPrefixes, usPrefixes);
    }

    @Test
    public void shouldReturnMessageForInvalidRegion(){
        String message = "No IP-Prefixes for the given Region:KK";
        webTestClient
                .get()
                .uri("/ip-ranges?region=KK")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                .expectBody(String.class).isEqualTo(message);
    }




}
