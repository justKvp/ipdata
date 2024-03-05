package ru.center.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "domains"
)
public class Domain extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigserial")
    private Long id;

    @Column(name = "domain", columnDefinition = "text")
    private String domain;

    @Column(name = "date_added", columnDefinition = "timestamp")
    @JsonProperty(value = "date_added")
    private Date dateAdded;

    @OneToMany(
            mappedBy = "domain",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @JsonProperty(value = "ip_addrs")
    public List<IpEntity> ips = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDomainName() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public static Domain findByDomain(String domain) {
        return (Domain)findAllByDomain(domain).firstResult();
    }

    public static PanacheQuery<Domain> findAllByDomain(String domain) {
        return find("domain", new Object[]{domain});
    }
}
