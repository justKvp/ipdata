package ru.center.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(
        name = "ipdata"
)
public class IpEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint unsigned")
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "domain_id",
            columnDefinition = "bigint unsigned"
    )
    @JsonIgnore
    private Domain domain;

    @Column(name = "ip", columnDefinition = "varchar(96)")
    private String ip;

    @Column(name = "date_added", columnDefinition = "timestamp")
    @JsonProperty(value = "date_added")
    private Date dateAdded;

    @Column(name = "comment", columnDefinition = "varchar(255)")
    @JsonProperty(value = "comment")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static IpEntity findByIp(String ipAddr) {
        return (IpEntity)findAllByIp(ipAddr).firstResult();
    }

    public static PanacheQuery<IpEntity> findAllByIp(String ipAddr) {
        return find("ip", new Object[]{ipAddr});
    }
}
