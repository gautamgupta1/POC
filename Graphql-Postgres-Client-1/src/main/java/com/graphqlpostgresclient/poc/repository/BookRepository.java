package com.graphqlpostgresclient.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.graphqlpostgresclient.poc.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
	
//    public default Iterable<Book> findAll(final SortedSet<String> selections) {
//        var query = getQuery(Sort.unsorted(), selections);
//        var entryTimestamp = LocalDateTime.now();
//        var response = query.getResultList();
//        logSQL(query, entryTimestamp, LocalDateTime.now());
//        return response;
//    }
}
