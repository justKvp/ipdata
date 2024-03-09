package ru.center.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.center.dto.IpAddRq;
import ru.center.dto.IpRq;
import ru.center.service.IpDataService;

@Path("/api/v1/ip")
public class IpResource {
    @Inject
    IpDataService ipDataService;

    @POST
    @Path("/getIp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIp(@Valid IpRq ipRq) {
        return ipDataService.getIp(ipRq);
    }

    @GET
    @Path("/getListIP")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllIps() {
        return ipDataService.getAllIps();
    }

    @PUT
    @Path("/addIp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addIp(@Valid IpAddRq ipRq) {
        return ipDataService.addIp(ipRq);
    }

    @POST
    @Path("/updateIp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIp(@Valid IpAddRq ipRq) {
        return ipDataService.updateIp(ipRq);
    }
}
