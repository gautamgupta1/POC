package com.graphqlpostgresclient.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graphqlpostgresclient.poc.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>
{
}
