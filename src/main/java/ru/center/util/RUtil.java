package ru.center.util;

import jakarta.ws.rs.core.Response;

public class RUtil {

    /**
     * http code 200 success
     **/
    public static Response success(Object obj) {
        return Response.ok(obj).build();
    }

    /**
     * http code 202 accepted
     **/
    public static Response accepted() {
        return Response.accepted().build();
    }

    /**
     * http code 412 success
     **/
    public static Response preconditionFailed(Object obj) {
        return Response.status(Response.Status.PRECONDITION_FAILED).entity(obj).build();
    }

    /**
     * http code 417 success
     **/
    public static Response expectationFailed(Object obj) {
        return Response.status(Response.Status.EXPECTATION_FAILED).entity(obj).build();
    }
}
