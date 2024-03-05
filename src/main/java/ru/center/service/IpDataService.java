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
            IpRs rs = new IpRs();
            rs.setIp(entity.getIp());
            rs.setDomainName(entity.getDomain().getDomainName());
            rs.setComment(entity.getComment());
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

        IpRs rs = new IpRs();
        rs.setIp(ipEntity.getIp());
        rs.setDomainName(ipEntity.getDomain().getDomainName());
        rs.setComment(ipEntity.getComment());
        return Response.ok(rs).build();
    }

    public Response updateIp(IpAddRq ipRq) {
        IpEntity ipEntity = IpEntity.findByIp(ipRq.getIp());
        if (ipEntity == null) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(new ErrorMsg("1", String.format("IP %s doesn't exist", ipRq.getIp())))
                    .build();
        }
        return Response.ok(ipRq).build();
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected void updateIpData(IpAddRq ipRq, IpEntity ipEntity) {
        //ipEntity.setDomain(ipRq.getDomainName());
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
