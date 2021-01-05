package aws.ipranges.domain;

import lombok.Builder;

@Builder
public class IpPrefix {
    private String region;
    private String service;
    private String network_border_group;

    public IpPrefix(){}

    public IpPrefix(String region, String service, String network_border_group) {
        this.region = region;
        this.service = service;
        this.network_border_group = network_border_group;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNetwork_border_group() {
        return network_border_group;
    }

    public void setNetwork_border_group(String network_border_group) {
        this.network_border_group = network_border_group;
    }


}
