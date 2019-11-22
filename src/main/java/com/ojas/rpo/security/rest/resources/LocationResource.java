package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.location.LocationDao;
import com.ojas.rpo.security.entity.Location;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/location")
public class LocationResource
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ObjectMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> list() throws IOException
    {
        this.logger.info("list()");

        ObjectWriter viewWriter;
        if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {
            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
        }
        List<Location> allEntries = this.locationDao.findAll();

        return allEntries;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Location read(@PathParam("id") Long id)
    {
        this.logger.info("read(id)");

        Location location = this.locationDao.find(id);
        if (location == null) {
            throw new WebApplicationException(404);
        }
        return location;
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Location create(Location location)
    {
        this.logger.info("create(): " + location);

        return this.locationDao.save(location);
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Location update(@PathParam("id") Long id, Location location)
    {
        this.logger.info("update(): " + location);

        return this.locationDao.save(location);
    }
 private boolean isAdmin()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getAuthorities().contains(Role.ADMIN);
    }
}
