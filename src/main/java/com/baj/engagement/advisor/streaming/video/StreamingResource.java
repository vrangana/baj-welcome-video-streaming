package com.baj.engagement.advisor.streaming.video;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Path("/fetchWelcomeVideo")
public class StreamingResource {

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Uni<Response> fetchWelcomeVideo() {
        File nf = new File("src/main/resources/Sample.mp4");
        if (!Files.exists(nf.toPath())) {
            return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND).build());
        }
        Response.ResponseBuilder response = Response.ok((Object) nf);
        response.header("Content-Disposition", "attachment;filename=" + nf.getName());
        Uni<Response> re = Uni.createFrom().item(response.build());
        return re;
    }
}