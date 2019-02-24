package com.example.hateaos.server.resource;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.hateaos.server.model.Company;
import com.example.hateaos.server.model.Person;
import com.example.hateaos.server.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Component
@Path( "/companies" ) 
@Tag(name = "companies")
public class CompanyResource {
    @Autowired private CompanyService service;
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Operation(
        description = "List all companies",
        responses = {
            @ApiResponse(
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))),
                links = {
                    @io.swagger.v3.oas.annotations.links.Link(
                       name = "self", 
                       operationRef = "#/paths/~1companies/get",
                       description = "List of Companies"
                    ),
                },
                description = "The list of companies",
                responseCode = "200"
            )
        }
    )
    public Response getCompanies(@Context UriInfo uriInfo) {
        return Response
            .ok(service.getCompanies())
            .links(Link.fromUriBuilder(uriInfo.getRequestUriBuilder()).rel("self").build())
            .build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Operation(
        description = "Find Company by Id",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Company.class)),
                links = {
                    @io.swagger.v3.oas.annotations.links.Link(
                       name = "self", 
                       operationRef = "#/paths/~1companies~1{id}/get",
                       description = "Find Company",
                       parameters = @LinkParameter(name = "id", expression = "$response.body#/id")
                    ),
                    @io.swagger.v3.oas.annotations.links.Link(
                        name = "staff", 
                        operationRef = "#/paths/~1companies~1{id}~1staff/get",
                        description = "Get Company Staff",
                        parameters = @LinkParameter(name = "id", expression = "$response.body#/id")
                    ),
                    @io.swagger.v3.oas.annotations.links.Link(
                        name = "collection", 
                        operationRef = "#/paths/~1companies/get",
                        description = "List Companies"
                    )
                },
                description = "Company details",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Company does not exist",
                responseCode = "404"
            )
        }
    )
    @Path("{id}")
    public Response getCompanyById(@Context UriInfo uriInfo, @PathParam("id") String id) {
        return service
            .findCompanyById(id)
            .map(company -> Response
                .ok(company)
                .links(
                    Link.fromUriBuilder(uriInfo
                            .getRequestUriBuilder())
                        .rel("self")
                        .build(),
                    Link.fromUriBuilder(uriInfo
                            .getBaseUriBuilder()
                            .path(CompanyResource.class))
                        .rel("collection")
                        .build(),
                    Link.fromUriBuilder(uriInfo
                           .getBaseUriBuilder()
                           .path(CompanyResource.class)
                           .path(CompanyResource.class, "getStaff"))
                        .rel("staff")
                        .build(id)
                 )
                .build())
            .orElseThrow(() -> new NotFoundException("The company with id '" + id + "' does not exists"));
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Operation(
        description = "Get Company Staff",
        responses = {
            @ApiResponse(
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Person.class))),
                links = {
                    @io.swagger.v3.oas.annotations.links.Link(
                        name = "self", 
                        operationRef = "#/paths/~1companies~1{id}~1staff/get",
                        description = "Staff",
                        parameters = @LinkParameter(name = "id", expression = "$response.body#/id")
                    ),
                    @io.swagger.v3.oas.annotations.links.Link(
                        name = "company", 
                        operationRef = "#/paths/~1companies~1{id}/get",
                        description = "Company",
                        parameters = @LinkParameter(name = "id", expression = "$response.body#/id")
                    )
                },
                description = "The Staff of the Company",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Company does not exist",
                responseCode = "404"
            )
        }
    )
    @Path("{id}/staff")
    public Response getStaff(@Context UriInfo uriInfo, @PathParam("id") String id) {
        return service
            .findCompanyById(id)
            .map(c -> service.getStaff(c))
            .map(staff -> Response
                .ok(staff)
                .links(
                    Link.fromUriBuilder(uriInfo
                            .getRequestUriBuilder())
                        .rel("self")
                        .build(),
                    Link.fromUriBuilder(uriInfo
                            .getBaseUriBuilder()
                            .path(CompanyResource.class)
                            .path(id))
                        .rel("company")
                        .build()
                 )
                .build())
            .orElseThrow(() -> new NotFoundException("The company with id '" + id + "' does not exists"));
    }

}
