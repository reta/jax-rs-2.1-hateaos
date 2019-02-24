package com.example.hateaos.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hateaos.server.model.Company;
import com.example.hateaos.server.model.Person;

@Service
public class CompanyService {
    private final Map<String, Company> companies = Map.ofEntries(
            Map.entry("1", new Company("1", "HATEOAS, Inc."))
        );

    private final Map<String, Collection<Person>> staff = Map.ofEntries(
            Map.entry("1", List.of(
                new Person("1", "john@smith.com", "John", "Smith"),
                new Person("2", "bob@smith.com", "Bob", "Smith")
            ))
        );

	public Collection<Company> getCompanies() {
		return companies.values();
	}

	public Optional<Company> findCompanyById(String id) {
        return Optional.ofNullable(companies.get(id));
    }
	
    public Collection<Person> getStaff(Company company) {
        return staff.get(company.getId());
    }
}
