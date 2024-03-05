package ru.center.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class IpRq {
    @Schema(required = true, description = "Ip address")
    @NotNull(message = "address does not exist")
    @JsonProperty(value = "ip_address")
    private String ip;

    @Schema(description = "Domain name")
    @JsonProperty(value = "domain")
    private String domainName;

    @Schema(description = "comment")
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
