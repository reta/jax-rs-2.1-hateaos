package com.example.hateaos.client;

import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HateaosClient {
	public static void main(String[] args) {
		final Client client = ClientBuilder.newClient();
		getAllCompanies(client);
		getAllCompany("1");
	}

    private static void getAllCompany(final String id) {
        final Client client = ClientBuilder.newClient();

        try (final Response response = client
                .target("http://localhost:8080/api/companies/{id}")
                .resolveTemplate("id", id)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get()) {
            
            final Optional<Link> staff = response
                 .getLinks()
                 .stream()
                 .filter(link -> Objects.equals(link.getRel(), "staff"))
                 .findFirst();
            
            staff.ifPresent(link -> {
                // follow the link here 
            });
            
        } finally {
            client.close();
        }
    }
    
    private static void folo(final Client client) {
        
    }

    private static void getAllCompanies(final Client client) {
        final Response response = client
			.target("http://localhost:8080/api/companies")
			.request()
			.accept(MediaType.APPLICATION_JSON)
			.get();
		
		try (response) {
			response.getLinks().forEach(System.out::println);
		}
    }
}
