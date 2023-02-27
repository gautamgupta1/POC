package com.graphqlpostgresclient.poc.resolver;

import static com.graphqlpostgresclient.poc.GraphQLHelper.addFieldSelections;
import static com.graphqlpostgresclient.poc.GraphQLHelper.addFragmentSelections;
import static com.graphqlpostgresclient.poc.GraphQLHelper.getField;
import static com.graphqlpostgresclient.poc.GraphQLHelper.getFragment;

import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graphqlpostgresclient.poc.model.Book;
import com.graphqlpostgresclient.poc.repository.BookRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class BookQuery implements GraphQLQueryResolver  {

	@Autowired
    private BookRepository bookRepository;
    
    public Iterable<Book> findAllBooks(final DataFetchingEnvironment environment) {
    	
    	var selections = addFieldSelections(getField(environment.getMergedField(), "findAllBooks"), new TreeSet<>());
        addFragmentSelections(getFragment(environment.getFragmentsByName(), "Book"), selections);
//        selections.addAll(CcCancelOrderRequest.getMandatorySelections());
    	
        return bookRepository.findAll();
    }
}
