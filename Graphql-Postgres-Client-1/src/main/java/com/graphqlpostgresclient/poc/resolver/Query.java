package com.graphqlpostgresclient.poc.resolver;

import com.graphqlpostgresclient.poc.model.Author;
import com.graphqlpostgresclient.poc.repository.AuthorRepository;
import com.graphqlpostgresclient.poc.repository.BookRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;

public class Query implements GraphQLQueryResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }



    public long countBooks() {
        return bookRepository.count();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public long countAuthors() {
        return authorRepository.count();
    }
}
