package ru.center.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import ru.center.dto.ErrorMsg;
import ru.center.dto.IpAddRq;
import ru.center.dto.IpRq;
import ru.center.dto.IpRs;
import ru.center.entity.Domain;
import ru.center.entity.IpEntity;
import ru.center.interfaces.CommonHelper;
import ru.center.util.RUtil;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class IpDataService implements CommonHelper {

    public Response getIp(IpRq ipRq) {
        IpEntity entity = IpEntity.findByIp(ipRq.getIp());
        if (entity != null) {
            IpRs rs = new IpRs(entity);
            return RUtil.success(rs);
        }
        return RUtil.expectationFailed(new ErrorMsg("1", String.format("IP %s doesn't exist", ipRq.getIp())));
    }

    public Response getAllIps() {
        List<IpEntity> ipEntityList = IpEntity
                .findAll(Sort.by("id"))
                .list();

        List<IpRs> irs = new ArrayList<>();
        for(IpEntity entity : ipEntityList) {
            irs.add(new IpRs(entity));
        }
        return RUtil.success(irs);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Response addIp(IpAddRq ipRq) {
        IpEntity ipEntity = IpEntity.findByIp(ipRq.getIp());
        if (ipEntity != null) {
            return RUtil.expectationFailed(new ErrorMsg("1", String.format("IP %s already exist", ipRq.getIp())));
        }

        if (ipRq.getDomainName().isEmpty()) {
            return RUtil.expectationFailed(new ErrorMsg("1", "Domain should not be Empty"));
        }

        Domain domain = Domain.findByDomain(ipRq.getDomainName());
        if (domain != null) {
            ipEntity = addIp(ipRq, domain);
        } else {
            ipEntity = addIpWithDomain(ipRq);
        }

        IpRs rs = new IpRs(ipEntity);
        return RUtil.success(rs);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Response updateIp(IpAddRq ipRq) {
        IpEntity ipEntity = IpEntity.findByIp(ipRq.getIp());
        if (ipEntity == null) {
            return RUtil.expectationFailed(new ErrorMsg("1", String.format("IP %s doesn't exist", ipRq.getIp())));
        }
        ipEntity = updateIpData(ipRq);
        IpRs rs = new IpRs(ipEntity);
        return RUtil.success(rs);
    }
}
