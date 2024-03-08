package ru.center.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import ru.center.dto.DomainRq;
import ru.center.dto.ErrorMsg;
import ru.center.entity.Domain;
import ru.center.util.RUtil;

import java.util.Date;

@ApplicationScoped
public class DomainDataService {

    public Response getAllDomains() {
        return Response.ok(Domain.findAll().list()).build();
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

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected Domain createNewDomain(String domainName) {
        Domain domain = new Domain();
        domain.setDomain(domainName);
        domain.setDateAdded(new Date());
        domain.persist();
        return domain;
    }
}
