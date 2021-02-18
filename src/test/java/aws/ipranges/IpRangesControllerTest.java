package aws.ipranges;

import aws.ipranges.controller.IpRangesController;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = IpRangesController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IpRangesControllerTest {

    private static MockWebServer mockBackEnd = new MockWebServer();
    private static IpRangesController controller;

    @BeforeAll
    static void setup() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        controller = new IpRangesController(WebClient.create(mockBackEnd.url("/").toString()));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize(){
        mockBackEnd.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .setBody("{\"syncToken\":\"1613581453\"," +
                                " \"createDate\": \"2021-02-17-17-04-13\"," +
                                "\"prefixes\":[" +
                                "{\"ip_prefix\":\"3.5.140.0/22\", " +
                                "\"region\":\"eu-northeast-2\"," +
                                "\"service\": \"AMAZON\", " +
                                "\"network_border_group\":\"eu-northeast-2\"}," +
                                "{\"ip_prefix\":\"4.5.140.0/22\", " +
                                "\"region\":\"us-northeast-2\"," +
                                "\"service\": \"AMAZON\", " +
                                "\"network_border_group\":\"us-northeast-2\"}]," +
                                "\"ipv6_prefixes\": [" +
                                "{\"ipv6_prefix\":\"2a05:d07a:a000::/40\", " +
                                "\"region\":\"eu-south-1\"," +
                                "\"service\": \"AMAZON\"," +
                                " \"network_border_group\":\"eu-south-1\"},"+
                                "{\"ipv6_prefix\":\"3a05:d07a:a000::/40\", " +
                                "\"region\":\"us-south-1\"," +
                                "\"service\": \"AMAZON\"," +
                                " \"network_border_group\":\"us-south-1\"}]}"
                        ));

    }


    @Test
    @Order(1)
    public void shouldFilterEuIpPrefixes() {
        String prefixes = controller.getIpRangesById("eu")
                    .map(response -> response.getBody())
                    .block();
        Assertions.assertEquals("3.5.140.0/22\n2a05:d07a:a000::/40\n", prefixes);
        }

    @Test
    @Order(2)
    public void shouldFilterUsIpPrefixes(){
        String prefixes = controller.getIpRangesById("us")
                .map(response -> response.getBody())
                .block();
        Assertions.assertEquals("4.5.140.0/22\n3a05:d07a:a000::/40\n", prefixes);
    }

    @Test
    @Order(3)
    public void shouldReturnAllIpPrefixes(){
        String prefixes = controller.getIpRangesById("all")
                .map(response -> response.getBody())
                .block();
        Assertions.assertEquals("3.5.140.0/22\n4.5.140.0/22\n2a05:d07a:a000::/40\n3a05:d07a:a000::/40\n", prefixes);
    }

    @Test
    @Order(4)
    public void shouldReturnMessageForInvalidRegion(){
        mockBackEnd.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                        .setBody("No IP-Prefixes for the given Region:kk"));
        String prefixes = controller.getIpRangesById("kk")
                .map(response -> response.getBody())
                .block();
        Assertions.assertEquals("No IP-Prefixes for the given Region:kk", prefixes);
    }
}






