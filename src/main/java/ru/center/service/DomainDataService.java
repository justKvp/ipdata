package ru.center.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import ru.center.dto.DomainRq;
import ru.center.dto.ErrorMsg;
import ru.center.entity.Domain;
import ru.center.interfaces.CommonHelper;
import ru.center.util.RUtil;

@ApplicationScoped
public class DomainDataService implements CommonHelper {

    public Response getAllDomains() {
        return RUtil.success(Domain
                .findAll(Sort.by("id"))
                .list()
        );
    }

    public Response getDomain(DomainRq domainRq) {
        Domain domain = Domain.findByDomain(domainRq.getDomainName());
        if (domain != null) {
            return RUtil.success(domain);
        }
        return RUtil.expectationFailed(new ErrorMsg("1", String.format("Domain %s doesn't exist", domainRq.getDomainName())));
    }

    public Response addDomain(DomainRq domainRq) {
        Domain domain = Domain.findByDomain(domainRq.getDomainName());
        if (domain != null) {
            return RUtil.expectationFailed(new ErrorMsg("1", String.format("Domain %s already exist", domainRq.getDomainName())));
        }

        if (domainRq.getDomainName().isEmpty()) {
            return RUtil.expectationFailed(new ErrorMsg("1", "Domain should not be Empty"));
        }
        domain = createNewDomain(domainRq.getDomainName());
        return RUtil.success(domain);
    }
}
