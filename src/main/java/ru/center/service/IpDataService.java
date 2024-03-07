package ru.center.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import ru.center.dto.ErrorMsg;
import ru.center.dto.IpAddRq;
import ru.center.dto.IpRq;
import ru.center.dto.IpRs;
import ru.center.entity.Domain;
import ru.center.entity.IpEntity;

import java.util.Date;

@ApplicationScoped
public class IpDataService {

    public Response getIp(IpRq ipRq) {
        IpEntity entity = IpEntity.findByIp(ipRq.getIp());
        if (entity != null) {
            IpRs rs = new IpRs(entity);
            return Response.ok(rs).build();
        }
        return Response.status(Response.Status.EXPECTATION_FAILED)
                .entity(new ErrorMsg("1", String.format("IP %s doesn't exist", ipRq.getIp())))
                .build();
    }

    public Response addIp(IpAddRq ipRq) {
        IpEntity ipEntity = IpEntity.findByIp(ipRq.getIp());
        if (ipEntity != null) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(new ErrorMsg("1", String.format("IP %s already exist", ipRq.getIp())))
                    .build();
        }

        if (ipRq.getDomainName().isEmpty()) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(new ErrorMsg("1", "Domain should not be Empty"))
                    .build();
        }

        Domain domain = Domain.findByDomain(ipRq.getDomainName());
        if (domain != null) {
            ipEntity = addIp(ipRq, domain);
        }
        else {
            ipEntity = addIpWithDomain(ipRq);
        }

        IpRs rs = new IpRs(ipEntity);
        return Response.ok(rs).build();
    }

    public Response updateIp(IpAddRq ipRq) {
        IpEntity ipEntity = IpEntity.findByIp(ipRq.getIp());
        if (ipEntity == null) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(new ErrorMsg("1", String.format("IP %s doesn't exist", ipRq.getIp())))
                    .build();
        }
        ipEntity = updateIpData(ipRq);

        IpRs rs = new IpRs(ipEntity);
        return Response.ok(rs).build();
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected IpEntity updateIpData(IpAddRq ipRq) {
        IpEntity ipEntity = IpEntity.findByIp(ipRq.getIp());
        if (!ipRq.getDomainName().isEmpty()) {
            Domain domain = Domain.findByDomain(ipRq.getDomainName());
            if (domain == null) {
                domain = createNewDomain(ipRq.getDomainName());
            }
            ipEntity.setDomain(domain);
        }

        if (!ipRq.getComment().isEmpty()) {
            ipEntity.setComment(ipRq.getComment());
        }
        return ipEntity;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected Domain createNewDomain(String domainName) {
        Domain domain = new Domain();
        domain.setDomain(domainName);
        domain.setDateAdded(new Date());
        domain.persist();
        return domain;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected IpEntity addIpWithDomain(IpAddRq ipRq) {
        Domain domain = new Domain();
        domain.setDomain(ipRq.getDomainName());
        domain.setDateAdded(new Date());
        domain.persist();

        IpEntity ipEntity = new IpEntity();
        ipEntity.setIp(ipRq.getIp());
        ipEntity.setDateAdded(new Date());
        ipEntity.setComment(ipRq.getComment());
        ipEntity.setDomain(domain);
        ipEntity.persist();
        return ipEntity;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected IpEntity addIp(IpAddRq ipRq, Domain domain) {
        IpEntity ipEntity = new IpEntity();
        ipEntity.setIp(ipRq.getIp());
        ipEntity.setDateAdded(new Date());
        ipEntity.setComment(ipRq.getComment());
        ipEntity.setDomain(domain);
        ipEntity.persist();
        return ipEntity;
    }
}
