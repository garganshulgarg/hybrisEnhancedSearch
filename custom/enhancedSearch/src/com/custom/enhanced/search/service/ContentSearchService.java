package com.custom.enhanced.search.service;

import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;

import java.util.Collection;

/**
 * The interface Content search service.
 * This ibterface is utilized for both Solr Indexing and Quering Operations related to Content Search.
 *
 * @author anshulgarg
 */
public interface ContentSearchService {

    /**
     * Find paragraph components for content page collection.
     *
     * @param page the page
     * @return the collection
     */
    Collection<CMSParagraphComponentModel> findParagraphComponentsForContentPage(ContentPageModel page);

    /**
     * Search search page data.
     * Making the call to Solr Server with Search Query Data and the results will be returned.
     * This method is specially utilized for Content Search Only.
     *
     * @param searchQueryData the search query data
     * @param searchType      the search type
     * @return the search page data
     */
    SearchPageData<SearchResultValueData> search(SolrSearchQueryData searchQueryData, String searchType);

    /**
     * Search text search page data.
     *
     * @param text       the text
     * @param searchType the search type
     * @return the search page data
     */
    SearchPageData<SearchResultValueData> searchText(String text, String searchType);
}
