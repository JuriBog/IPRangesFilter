package aws.ipranges.domain;


public class Ipv4Prefix extends IpPrefix {

    Ipv4Prefix(String region, String service, String network_border_group) {
        super(region, service, network_border_group);
    }

    public Ipv4Prefix(){
        super();
    }

    public String getIp_prefix() {
        return ip_prefix;
    }

    public void setIp_prefix(String ip_prefix) {
        this.ip_prefix = ip_prefix;
    }

    private java.lang.String ip_prefix;

}
