package aws.ipranges;

import aws.ipranges.controller.IpRangesController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@WebFluxTest(controllers = IpRangesController.class)
public class IpRangesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IpRangesController controller;


    @Test
    public void shouldReturnIpPrefixes() {
        Mono<ResponseEntity<String>> ipPrefixes = Mono.just(ResponseEntity.ok()
                .body("35.180.0.0/16\n" +
                                "150.222.81.0/24\n" +
                                "52.219.168.0/24\n" +
                                "52.93.17.0/24\n" +
                                "52.95.150.0/24\n" +
                                "2600:1f16:8000::/36\n" +
                                "2600:1ff8:6000::/40\n" +
                                "2600:1ffa:5000::/40\n" +
                                "2600:1fff:5000::/40\n" +
                                "2600:1f1b:8000::/36\n" +
                                "2600:1ffe:2000::/40\n" +
                                "2600:1f14:fff:f800::/53\n" +
                                "2406:da00:ff00::6b17:ff00/122\n" +
                                "2600:1f18:7fff:f800::/53\n" +
                                "2600:1f1c:7ff:f800::/53\n" +
                                "2620:108:700f::36f4:34c0/122\n" +
                                "2600:1f1c:fff:f800::/53\n" +
                                "2620:108:700f::36f5:a800/122\n" +
                                "2600:1f18:3fff:f800::/53\n" +
                                "2406:da00:ff00::36f3:1fc0/122\n" +
                                "2620:107:300f::36f1:2040/122\n" +
                                "2620:107:300f::36b7:ff80/122\n" +
                                "2600:1f14:7ff:f800::/53"));

        Mockito.when(controller.getIpRangesById("EU")).thenReturn(ipPrefixes);

        webTestClient
                .get()
                .uri("/ip-ranges?region=EU")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                .expectBody(String.class)
                .value(val -> {
                    val.contains(ipPrefixes.block().toString());
                });
    }

    @Test
    public void shouldReturnNoValue(){
        Mono<ResponseEntity<String>> response = Mono.just(ResponseEntity.ok().contentType(MediaType.valueOf("text/plain;charset=UTF-8")).body("No IP-Prefixes for the given Region:KK"));
        Mockito.when(controller.getIpRangesById("KK")).thenReturn(response);
        webTestClient
                .get()
                .uri("/ip-ranges?region=KK")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                .expectBody(String.class)
                .value(val -> {
                    val.contains(response.block().toString());
                });

    }



}
