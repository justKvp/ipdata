package ru.center.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ru.center.entity.IpEntity;

import java.util.List;

public class DomainRq {
    @Schema(required = true, description = "Domain name")
    @NotNull(message = "domain does not exist")
    @JsonProperty(value = "domain")
    private String domainName;

    @Schema(required = true, description = "Ip addresses")
    @JsonProperty(value = "ip_addresses")
    private List<IpEntity> ips;

    public String getDomainName() {
        return domainName;
    }

    public List<IpEntity> getIps() {
        return ips;
    }
}
