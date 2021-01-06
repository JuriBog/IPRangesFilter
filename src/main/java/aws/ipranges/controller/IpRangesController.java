package aws.ipranges.controller;

import aws.ipranges.domain.IpRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/ip-ranges")
public class IpRangesController {

    private WebClient webClient;

    @Value("${aws.ip-ranges.url}")
    private String awsUrl;

    public IpRangesController(WebClient client){
        this.webClient = client;
    }

    @GetMapping
    public Mono<ResponseEntity<String>> getIpRangesById(@RequestParam("region") String region){
        return webClient
                .get()
                .uri(awsUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new Exception("Server Error"))
                )
                .bodyToMono(IpRange.class)
                .map(ipRange -> {

                    List<String> ipv4 = ipRange.getPrefixes().stream()
                            .filter(ipPrefix -> ipPrefix.getRegion().toLowerCase().contains(region.toLowerCase()) || region.toLowerCase().contains("all"))
                            .map(ip -> ip.getIp_prefix()+System.getProperty("line.separator"))
                            .collect(Collectors.toList());

                    List<String> ipv6 = ipRange.getIpv6_prefixes().stream()
                            .filter(ipPrefix -> ipPrefix.getRegion().toLowerCase().contains(region.toLowerCase()) || region.toLowerCase().contains("all"))
                            .map(ip -> ip.getIpv6_prefix()+System.getProperty("line.separator"))
                            .collect(Collectors.toList());

                   String output = Stream.concat(ipv4.stream(), ipv6.stream())
                           .collect(Collectors.toList())
                           .toString()
                           .replace("[", "")
                           .replace("]", "")
                           .replace(",", "")
                           .replace(" ", "");

                   if(output.isEmpty())
                       return ResponseEntity.ok().contentType(MediaType.valueOf("text/plain; charset=UTF-8")).body("No IP-Prefixes for the given Region:" + region);

                   return ResponseEntity.ok().contentType(MediaType.valueOf("text/plain; charset=UTF-8")).body(output);
                });
    }
}
