package ru.center.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class IpAddRq {
    @Schema(required = true, description = "Ip address")
    @NotNull(message = "address does not exist")
    @JsonProperty(value = "ip_address")
    private String ip;

    @Schema(required = true, description = "Domain name")
    @NotNull(message = "domain does not exist")
    @JsonProperty(value = "domain")
    private String domainName;

    @Schema(required = true, description = "comment")
    @JsonProperty(value = "comment")
    private String comment;

    public String getIp() {
        return ip;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getComment() {
        return comment;
    }
}
