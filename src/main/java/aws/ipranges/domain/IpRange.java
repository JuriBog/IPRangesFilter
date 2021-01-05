package aws.ipranges.domain;

import lombok.Builder;
import org.inferred.freebuilder.FreeBuilder;

import java.util.List;

@Builder
public class IpRange {
    private java.lang.String syncToken;
    private java.lang.String createDate;
    private List<Ipv4Prefix> prefixes;
    private List<Ipv6Prefix> ipv6_prefixes;

    public IpRange(String syncToken, String createDate, List<Ipv4Prefix> prefixes, List<Ipv6Prefix> ipv6_prefixes) {
        this.syncToken = syncToken;
        this.createDate = createDate;
        this.prefixes = prefixes;
        this.ipv6_prefixes = ipv6_prefixes;
    }

    public IpRange(){}

    public String getSyncToken() {
        return syncToken;
    }

    public void setSyncToken(String syncToken) {
        this.syncToken = syncToken;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<Ipv4Prefix> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(List<Ipv4Prefix> prefixes) {
        this.prefixes = prefixes;
    }

    public List<Ipv6Prefix> getIpv6_prefixes() {
        return ipv6_prefixes;
    }

    public void setIpv6_prefixes(List<Ipv6Prefix> ipv6_prefixes) {
        this.ipv6_prefixes = ipv6_prefixes;
    }

}
