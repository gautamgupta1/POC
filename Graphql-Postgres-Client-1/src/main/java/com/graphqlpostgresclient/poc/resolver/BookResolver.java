package com.graphqlpostgresclient.poc.resolver;

import com.graphqlpostgresclient.poc.model.Author;
import com.graphqlpostgresclient.poc.model.Book;
import com.graphqlpostgresclient.poc.repository.AuthorRepository;

import graphql.kickstart.tools.GraphQLResolver;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findById(book.getAuthor()
                                             .getId())
                               .orElseThrow(null);
    }
}
