package aws.ipranges.domain;

import lombok.Builder;


public class Ipv6Prefix extends IpPrefix{

    private String ipv6_prefix;

    Ipv6Prefix(String region, String service, String network_border_group) {
        super(region, service, network_border_group);
    }

    Ipv6Prefix(){
        super();
    }

    public String getIpv6_prefix() {
        return ipv6_prefix;
    }

    public void setIpv6_prefi(String ipv6_prefix) {
        this.ipv6_prefix = ipv6_prefix;
    }

}
