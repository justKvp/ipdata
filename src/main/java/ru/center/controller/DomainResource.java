package ru.center.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.center.dto.DomainRq;
import ru.center.service.DomainDataService;

@Path("/api/v1/domain")
public class DomainResource {
    @Inject
    DomainDataService domainDataService;

    @POST
    @Path("/getDomain")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDomain(@Valid DomainRq domainRq) {
        return domainDataService.getDomain(domainRq);
    }

    @GET
    @Path("/getAllDomains")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDomains() {
        return domainDataService.getAllDomains();
    }

    @PUT
    @Path("/addDomain")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDomain(@Valid DomainRq domainRq) {
        return domainDataService.addDomain(domainRq);
    }
}
