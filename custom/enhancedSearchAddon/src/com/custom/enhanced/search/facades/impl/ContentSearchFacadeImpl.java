/**
 *
 */
package com.custom.enhanced.search.facades.impl;

import com.custom.enhanced.search.constants.EnhancedSearchConstants;
import com.custom.enhanced.search.data.ContentData;
import com.custom.enhanced.search.facades.ContentSearchFacade;
import com.custom.enhanced.search.service.ContentSearchService;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService.Nothing;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import java.util.Collection;


/**
 * Facade to provide the content search functionality to the front end.
 *
 * @author anshulgarg
 */
public class ContentSearchFacadeImpl implements ContentSearchFacade {
    private Converter<SearchResultValueData, ContentData> contentSearchResultConverter;
    private Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder;
    private ContentSearchService contentSearchService;
    private ThreadContextService threadContextService;


    @Override
    public Collection<ContentData> contentTextSearch(final String text) {
        return this.getThreadContextService()
                .executeInContext(new ThreadContextService.Executor<Collection<ContentData>, Nothing>() {

                    @Override
                    public Collection<ContentData> execute() throws Nothing {
                        final SearchPageData<SearchResultValueData> contentSearch = contentSearchService.searchText(text,
                                EnhancedSearchConstants.CONTENT_SEARCH);
                        return Converters.convertAll(contentSearch.getResults(), contentSearchResultConverter);
                    }
                });
    }

    @Override
    public Collection<ContentData> contentTextSearch(final SearchStateData searchState) {
        Assert.notNull(searchState, "SearchStateData must not be null.");

        return this.getThreadContextService()
                .executeInContext(new ThreadContextService.Executor<Collection<ContentData>, Nothing>() {

                    @Override
                    public Collection<ContentData> execute() throws Nothing {
                        final SearchPageData<SearchResultValueData> contentSearch = contentSearchService
                                .search(decodeState(searchState), EnhancedSearchConstants.CONTENT_SEARCH);
                        return Converters.convertAll(contentSearch.getResults(), contentSearchResultConverter);
                    }
                });
    }


    /**
     * Decodes the searchState to convert it into a SolrSearchQueryData. It could be easier to use a simple String, but
     * trying to maintain the hybris-solr structure as much as possible for future improvements on the extension.
     *
     * @param searchState
     * @return A SolrSearchQueryData object.
     */
    protected SolrSearchQueryData decodeState(final SearchStateData searchState) {
        return this.searchQueryDecoder.convert(searchState.getQuery());
    }

    protected ThreadContextService getThreadContextService() {
        return threadContextService;
    }

    @Required
    public void setThreadContextService(final ThreadContextService threadContextService) {
        this.threadContextService = threadContextService;
    }

    @Required
    public void setContentSearchService(final ContentSearchService contentSearchService) {
        this.contentSearchService = contentSearchService;
    }

    @Required
    public void setContentSearchResultConverter(final Converter<SearchResultValueData, ContentData> contentSearchResultConverter) {
        this.contentSearchResultConverter = contentSearchResultConverter;
    }


    @Required
    public void setSearchQueryDecoder(final Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder) {
        this.searchQueryDecoder = searchQueryDecoder;
    }


}