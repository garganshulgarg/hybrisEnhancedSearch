/**
 *
 */
package com.custom.enhanced.search.querybuilder.impl;

import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.commerceservices.search.solrfacetsearch.querybuilder.impl.AbstractFreeTextQueryBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.Map;


/**
 * TextQueryBuilder created to give more flexibility in the fields used to create the solr search and the boosting given
 * to each of them.
 *
 * @author anshulgarg
 *
 */
public class EnhancedSearchFreeTextQueryBuilder extends AbstractFreeTextQueryBuilder
{
	private static final Logger LOG = LoggerFactory.getLogger(EnhancedSearchFreeTextQueryBuilder.class);

	//constants to be defined once the bean is created
	private static final String EQUALS = "equals";
	private static final String FUZZY = "fuzzy";
	private static final String WILDCARD = "wildcard";
	private static final String FUZZY_PROXIMITY = "fuzzyProximity";

	private String propertyName;
	private Map<String, Double> textFieldSearchConfig;
	private Map<String, Double> otherFieldSearchConfig;

	/**
	 * Method copied from the abstract parent class and modified to remove the boosting. Boost will be managed in the map
	 * so not required here as a parameter.
	 *
	 * @param searchQuery
	 * @param indexedProperty
	 * @param fullText
	 * @param textWords
	 */
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String fullText,
			final String[] textWords)
	{
		addFreeTextQuery(searchQuery, indexedProperty, fullText);

		if (textWords != null && textWords.length > 1)
		{
			for (final String word : textWords)
			{
				addFreeTextQuery(searchQuery, indexedProperty, word);
			}
		}
	}

	/**
	 * Method copied from the abstract parent class and modified to remove the boosting. Boost will be managed in the map
	 * so not required here as a parameter.
	 *
	 * @param searchQuery
	 * @param indexedProperty
	 * @param value
	 */
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String value)
	{
		final String field = indexedProperty.getName();
		if (!indexedProperty.isFacet())
		{
			if ("text".equalsIgnoreCase(indexedProperty.getType()))
			{
				this.buildFreeTextQueryFields(searchQuery, indexedProperty, value, this.textFieldSearchConfig, field);
			}
			else
			{
				this.buildFreeTextQueryFields(searchQuery, indexedProperty, value, this.otherFieldSearchConfig, field);
			}
		}
		else
		{
			LOG.warn("Not searching " + indexedProperty
					+ ". Free text search not available in facet property. Configure an additional text property for searching.");
		}
	}

	/**
	 * Overriden the method defined in the interface.
	 *
	 * @param searchQuery
	 * @param fullText
	 * @param textWords
	 */
	@Override
	public void addFreeTextQuery(final SearchQuery searchQuery, final String fullText, final String[] textWords)
	{
		final IndexedProperty indexedProperty = searchQuery.getIndexedType().getIndexedProperties().get(this.propertyName);
		if (indexedProperty != null)
		{
			addFreeTextQuery(searchQuery, indexedProperty, fullText, textWords);
		}
	}

	/**
	 * Adds each of the fields to the query string if required based on the map configuration.
	 *
	 * @param searchQuery
	 * @param indexedProperty
	 * @param value
	 * @param configMap
	 * @param field
	 */
	protected void buildFreeTextQueryFields(final SearchQuery searchQuery, final IndexedProperty indexedProperty,
			final String value, final Map<String, Double> configMap, final String field)
	{
		if (null != configMap)
		{
			//equality on the check
			if (null != configMap.get(EnhancedSearchFreeTextQueryBuilder.EQUALS))
			{
				addFreeTextQuery(searchQuery, field, value.toLowerCase(), "",
						configMap.get(EnhancedSearchFreeTextQueryBuilder.EQUALS).doubleValue());
			}
			//wildcard
			if (null != configMap.get(EnhancedSearchFreeTextQueryBuilder.WILDCARD))
			{
				addFreeTextQuery(searchQuery, field, value.toLowerCase(), "*",
						configMap.get(EnhancedSearchFreeTextQueryBuilder.WILDCARD).doubleValue());
			}
			//fuzzy search
			if (null != configMap.get(EnhancedSearchFreeTextQueryBuilder.FUZZY))
			{
				final String proximity = configMap.get(EnhancedSearchFreeTextQueryBuilder.FUZZY_PROXIMITY) != null
						? configMap.get(EnhancedSearchFreeTextQueryBuilder.FUZZY_PROXIMITY).toString() : StringUtils.EMPTY;
				addFreeTextQuery(searchQuery, field, value.toLowerCase(), "~" + proximity,
						configMap.get(EnhancedSearchFreeTextQueryBuilder.FUZZY).doubleValue());
			}
		}
		else
		{
			LOG.warn("Property {} not used in the search as the related configuration map is not properly configured",
					this.propertyName);
		}
	}

	@Required
	public void setPropertyName(final String propertyName)
	{
		this.propertyName = propertyName;
	}

	/**
	 * Sets the map with the search config for text fields and validates that all the map entries have a value associated
	 * with them.
	 *
	 * Not required as field could be text or other type.
	 *
	 * @param textFieldSearchConfig
	 */
	public void setTextFieldSearchConfig(final Map<String, Double> textFieldSearchConfig)
	{
		for (final String key : textFieldSearchConfig.keySet())
		{
			if (null == textFieldSearchConfig.get(key))
			{
				LOG.error("Map for textFieldSearchConfig not properly configured, missing values.");
				this.textFieldSearchConfig = null;
				return;
			}
		}
		this.textFieldSearchConfig = textFieldSearchConfig;
	}

	/**
	 * Sets the map with the search config for non-text fields and validates that all the map entries have a value
	 * associated with them.
	 *
	 * Not required as field could be text or other type.
	 *
	 * @param otherFieldSearchConfig
	 */
	public void setOtherFieldSearchConfig(final Map<String, Double> otherFieldSearchConfig)
	{
		for (final String key : otherFieldSearchConfig.keySet())
		{
			if (null == otherFieldSearchConfig.get(key))
			{
				LOG.error("Map for otherFieldSearchConfig not properly configured, missing values.");
				this.otherFieldSearchConfig = null;
				return;
			}
		}
		this.otherFieldSearchConfig = otherFieldSearchConfig;
	}
}
