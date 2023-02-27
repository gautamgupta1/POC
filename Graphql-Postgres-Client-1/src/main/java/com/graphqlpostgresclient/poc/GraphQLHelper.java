package com.graphqlpostgresclient.poc;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;

import org.apache.commons.lang3.StringUtils;

import graphql.execution.MergedField;
import graphql.language.Field;
import graphql.language.FragmentDefinition;
import graphql.language.Selection;
import graphql.language.SelectionSet;

public class GraphQLHelper {

	   private GraphQLHelper() {
	    }

	    private static final String TYPE_NAME_FRAGMENT = "__typename";

	    public static final String DATE_FORMAT = "yyyy-MM-dd";
	    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	    /**
	     * Helper function to add all field selections from {@link Field}.
	     *
	     * @param field      the field
	     * @param selections the selections
	     * @return added field selections
	     */
	    public static SortedSet<String> addFieldSelections(final Field field, final SortedSet<String> selections) {
	        var selectionSets = Optional.ofNullable(field).map(Field::getSelectionSet).orElse(null);
	        return addSelections(selectionSets, selections);
	    }

	    /**
	     * Helper function to add all field selections from {@link FragmentDefinition}.
	     *
	     * @param fragmentDefinition the fragmentDefinition
	     * @param selections         the selections
	     * @return added field selections
	     */
	    public static SortedSet<String> addFragmentSelections(final FragmentDefinition fragmentDefinition, final SortedSet<String> selections) {
	        var selectionSets = Optional.ofNullable(fragmentDefinition).map(FragmentDefinition::getSelectionSet).orElse(null);
	        return addSelections(selectionSets, selections);
	    }

	    private static SortedSet<String> addSelections(final SelectionSet selectionSet, final SortedSet<String> selections) {
	        Optional.ofNullable(selectionSet).map(SelectionSet::getSelections)
	                .ifPresent(selectionFields -> selectionFields.forEach(selection -> {
	                    // only add selections without any more child
	                    // selection with child represent associations which will be resolved at the Resolver
	                    if (selection instanceof Field && ((Field) selection).getSelectionSet() == null) {
	                        var name = ((Field) selection).getName();
	                        if (!StringUtils.equalsAnyIgnoreCase(TYPE_NAME_FRAGMENT, name)) {
	                            selections.add(name);
	                        }
	                    }
	                }));
	        return selections;
	    }

	    /**
	     * Helper function to get {@link Field} from {@link MergedField} based on the query string.
	     *
	     * @param mergedField the mergedField
	     * @param query       the query
	     * @return {@link Field} from {@link MergedField} based on the query string
	     */
	    public static Field getField(final MergedField mergedField, final String query) {
	        return getField(mergedField.getFields(), query);
	    }

	    /**
	     * Helper function to get {@link Field} from {@link MergedField} based on the query and sub query string.
	     *
	     * @param mergedField the mergedField
	     * @param query       the query
	     * @param subQuery    the subQuery
	     * @return {@link Field} from {@link MergedField} based on the query and sub query string
	     */
	    public static Field getField(final MergedField mergedField, final String query, final String subQuery) {
	        // first get the selection field based on query string
	        var queryField = mergedField.getFields().stream()
	                .filter(Objects::nonNull)
	                .filter(field -> StringUtils.equalsAnyIgnoreCase(query, field.getName()))
	                .map(Field::getSelectionSet)
	                .filter(Objects::nonNull)
	                .map(SelectionSet::getSelections)
	                .findFirst();
	        return queryField.map(selections -> getField(selections, subQuery)).orElse(null);
	    }

	    private static <T extends Selection> Field getField(final List<T> selections, final String query) {
	        for (var selection : selections) {
	            if (selection instanceof Field && StringUtils.equalsAnyIgnoreCase(query, ((Field) selection).getName())) {
	                return (Field) selection;
	            }
	        }
	        return null;
	    }

	    /**
	     * Helper function to get {@link FragmentDefinition} from fragment map based on the resource.
	     *
	     * @param fragments the fragments
	     * @param resource  the resource
	     * @return {@link FragmentDefinition} from fragment map based on the resource
	     */
	    public static FragmentDefinition getFragment(final Map<String, FragmentDefinition> fragments, final String resource) {
	        return fragments.values().stream()
	                .filter(definition -> definition != null && definition.getTypeCondition() != null && StringUtils.equalsAnyIgnoreCase(resource,
	                        definition.getTypeCondition().getName()))
	                .findFirst()
	                .orElse(null);
	    }
}
