package fish.payara.examples.azure.k8s;


import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/data")
@Singleton
public class UserDataEndPoint {

    @Inject
    private UserDataService userDataService;

    @POST
    public Response createUser(@Valid UserData data, @Context UriInfo uriInfo) {
        UserData newUser = userDataService.store(data);
        return Response.created(UriBuilder.fromPath(uriInfo.getPath()).path("{id}").build(newUser.getId())).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") @NotNull Integer id) {
        return userDataService.retrieve(id).map(Response::ok).map(Response.ResponseBuilder::build)
                .orElseThrow(NotFoundException::new);
    }

    @GET
    @Path("/all")
    public List<UserData> getAllUsers() {
        return userDataService.listAll();
    }
}

