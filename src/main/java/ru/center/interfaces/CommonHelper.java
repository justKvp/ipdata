package ru.center.interfaces;

import jakarta.transaction.Transactional;
import ru.center.dto.IpAddRq;
import ru.center.entity.Domain;
import ru.center.entity.IpEntity;

import java.util.Date;

public interface CommonHelper {
    @Transactional(value = Transactional.TxType.MANDATORY, rollbackOn = Exception.class)
    default Domain createNewDomain(String domainName) {
        Domain domain = new Domain();
        domain.setDomain(domainName);
        domain.setDateAdded(new Date());
        domain.persist();
        return domain;
    }

    @Transactional(value = Transactional.TxType.MANDATORY, rollbackOn = Exception.class)
    default IpEntity addIpWithDomain(IpAddRq ipRq) {
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

    @Transactional(value = Transactional.TxType.MANDATORY, rollbackOn = Exception.class)
    default IpEntity addIp(IpAddRq ipRq, Domain domain) {
        IpEntity ipEntity = new IpEntity();
        ipEntity.setIp(ipRq.getIp());
        ipEntity.setDateAdded(new Date());
        ipEntity.setComment(ipRq.getComment());
        ipEntity.setDomain(domain);
        ipEntity.persist();
        return ipEntity;
    }

    @Transactional(value = Transactional.TxType.MANDATORY, rollbackOn = Exception.class)
    default IpEntity updateIpData(IpAddRq ipRq) {
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
}
