package aws.ipranges.domain;



public class Ipv6Prefix extends IpPrefix{

    private String ipv6_prefix;


    public String getIpv6_prefix() {
        return ipv6_prefix;
    }

    public void setIpv6_prefix(String ipv6_prefix) {
        this.ipv6_prefix = ipv6_prefix;
    }

}
