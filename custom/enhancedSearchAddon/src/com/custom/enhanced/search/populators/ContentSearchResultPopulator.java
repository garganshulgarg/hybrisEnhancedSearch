/**
 *
 */
package com.custom.enhanced.search.populators;

import com.custom.enhanced.search.data.ContentData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * Populates the information retrieve from the content search into a ContentData object to be sent to the front end.
 *
 * @author anshulgarg
 *
 */
public class ContentSearchResultPopulator implements Populator<SearchResultValueData, ContentData>
{

	@Override
	public void populate(final SearchResultValueData source, final ContentData target) throws ConversionException
	{
		//populate page id, page name and label
		target.setId(this.<String> getValue(source, "uid"));
		target.setName(this.<String> getValue(source, "name"));
		target.setLabel(this.<String> getValue(source, "label"));
	}

	/**
	 * Gets the value of the property with propertyName from the list of results from solr.
	 *
	 * @param source
	 * @param propertyName
	 * @return the value of the property with propertyName from the list of results from solr.
	 */
	protected <T> T getValue(final SearchResultValueData source, final String propertyName)
	{
		if (source.getValues() == null)
		{
			return null;
		}

		// DO NOT REMOVE the cast (T) below, while it should be unnecessary it is required by the javac compiler
		return (T) source.getValues().get(propertyName);
	}
}
