package com.graphqlpostgresclient.poc.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Employee {

	private Long id;
	private String name;
	
}

