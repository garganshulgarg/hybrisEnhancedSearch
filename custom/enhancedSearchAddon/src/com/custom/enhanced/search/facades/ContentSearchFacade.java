/**
 *
 */
package com.custom.enhanced.search.facades;

import com.custom.enhanced.search.data.ContentData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;

import java.util.Collection;


/**
 * Facade to provide the content search functionality to the front end.
 *
 * @author anshulgarg
 *
 */
public interface ContentSearchFacade
{
	/**
	 * Invokes the solr search based on the information passed using the SearchStateData object.
	 *
	 * @param text
	 * @return A collection on ContentData object with the relevant content search results.
	 */
	Collection<ContentData> contentTextSearch(String text);

	/**
	 * Invokes the solr search based on the information passed using the SearchStateData object.
	 *
	 * @param searchState
	 * @return A collection on ContentData object with the relevant content search results.
	 */
	Collection<ContentData> contentTextSearch(SearchStateData searchState);



}
