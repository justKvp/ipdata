package ru.center.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ru.center.entity.IpEntity;

import java.util.Date;

@JsonPropertyOrder({"ip_address", "domain", "date_added", "comment"})
public class IpRs {
    @Schema(required = true, description = "id")
    @NotNull(message = "id not exist")
    @JsonProperty(value = "id")
    private Long id;

    @Schema(required = true, description = "Ip address")
    @NotNull(message = "address does not exist")
    @JsonProperty(value = "ip_address")
    private String ip;

    @Schema(description = "Domain name")
    @JsonProperty(value = "domain")
    private String domainName;

    @Schema(description = "date_added")
    @JsonProperty(value = "date_added")
    private Date dateAdded;

    @Schema(description = "comment")
    @JsonProperty(value = "comment")
    private String comment;

    public IpRs(IpEntity ipEntity) {
        this.id = ipEntity.getId();;
        this.ip = ipEntity.getIp();
        this.domainName = ipEntity.getDomain().getDomainName();
        this.dateAdded = ipEntity.getDateAdded();
        this.comment = ipEntity.getComment();
    }

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getDomainName() {
        return domainName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public String getComment() {
        return comment;
    }
}
