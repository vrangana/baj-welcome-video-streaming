package com.baj.engagement.advisor.customer.welcome;


import com.baj.engagement.advisor.customer.welcome.model.WelcomeMessage;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("welcome")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WelcomeMessageResource {
    @Inject
    Mutiny.SessionFactory sessionFactory;

    @GET
    public Uni<List<WelcomeMessage>> get() {
        return sessionFactory.withTransaction((s, t) ->
                s.createNamedQuery("WelcomeMessage.findAll", WelcomeMessage.class).getResultList()
        );
    }
    @GET
    @Path("{id}")
    public Uni<WelcomeMessage> getById(Long id) {
        return sessionFactory.withTransaction((s,t) -> s.find(WelcomeMessage.class, id));
    }
//    @GET
//    public Uni<WelcomeMessage> getByLang(@QueryParam("languageCode") String languageCode) {
//        return sessionFactory.withTransaction((s,t) -> s.find(WelcomeMessage.class, languageCode));
//    }
    @POST
    public Uni<Response> create(WelcomeMessage welcomeMessage) {
        if (welcomeMessage == null || welcomeMessage.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        return sessionFactory.withTransaction((s, t) -> s.persist(welcomeMessage))
                .replaceWith(Response.ok(welcomeMessage).status(Response.Status.CREATED)::build);
    }
    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return sessionFactory.withTransaction((s, t) -> s.find(WelcomeMessage.class, id)
                .onItem().ifNull().failWith(new WebApplicationException("Welcome message missing on DB.", NOT_FOUND))
                .call(s::remove).replaceWith(Response.ok().status(Response.Status.NO_CONTENT)::build));
    }
}
