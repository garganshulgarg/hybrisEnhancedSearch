package com.custom.enhanced.search.service.impl;

import com.custom.enhanced.search.constants.EnhancedSearchConstants;
import com.custom.enhanced.search.dao.ContentSearchDao;
import com.custom.enhanced.search.service.ContentSearchService;
import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.*;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.Collections;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

/**
 * The type Content search service.
 *
 * @author anshulgarg
 */
public class ContentSearchServiceImpl implements ContentSearchService {

    private ContentSearchDao contentSearchDao;
    private Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> contentSearchQueryPageableConverter;
    private Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter;
    private Converter<SolrSearchResponse, SearchPageData<SearchResultValueData>> contentSearchResponseConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<CMSParagraphComponentModel> findParagraphComponentsForContentPage(ContentPageModel page) {
        return contentSearchDao.findParagraphComponentsForContentPage(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchPageData<SearchResultValueData> search(SolrSearchQueryData searchQueryData, String searchType) {
        return doSearch(searchQueryData, searchType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchPageData<SearchResultValueData> searchText(String text, String searchType) {
        final SolrSearchQueryData searchQueryData = new SolrSearchQueryData();
        searchQueryData.setFreeTextSearch(text);
        searchQueryData.setFilterTerms(Collections.<SolrSearchQueryTermData>emptyList());

        return doSearch(searchQueryData, searchType);
    }


    protected SearchPageData<SearchResultValueData> doSearch(final SolrSearchQueryData searchQueryData, final String searchType) {
        validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");

        //TODO For making the Content Data as Pageable pass the pageable Data over here.
        final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSolrSearchQueryPageableData(searchQueryData, null);
        if (searchType.equalsIgnoreCase(EnhancedSearchConstants.CONTENT_SEARCH)) {
            SolrSearchRequest solrSearchRequest = contentSearchQueryPageableConverter.convert(searchQueryPageableData);
            SolrSearchResponse solrSearchResponse = searchRequestConverter.convert(solrSearchRequest);
            return contentSearchResponseConverter.convert(solrSearchResponse);
        }


        return null;
    }

    protected SearchQueryPageableData<SolrSearchQueryData> buildSolrSearchQueryPageableData(final SolrSearchQueryData searchQueryData, final PageableData pageableData) {
        final SearchQueryPageableData<SolrSearchQueryData> solrSearchQueryDataSearchQueryPageableData = new SearchQueryPageableData<>();
        solrSearchQueryDataSearchQueryPageableData.setSearchQueryData(searchQueryData);
        solrSearchQueryDataSearchQueryPageableData.setPageableData(pageableData);
        return solrSearchQueryDataSearchQueryPageableData;
    }

    @Required
    public void setContentSearchDao(ContentSearchDao contentSearchDao) {
        this.contentSearchDao = contentSearchDao;
    }

    @Required
    public void setContentSearchQueryPageableConverter(Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> contentSearchQueryPageableConverter) {
        this.contentSearchQueryPageableConverter = contentSearchQueryPageableConverter;
    }

    @Required
    public void setSearchRequestConverter(Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter) {
        this.searchRequestConverter = searchRequestConverter;
    }

    @Required
    public void setContentSearchResponseConverter(Converter<SolrSearchResponse, SearchPageData<SearchResultValueData>> contentSearchResponseConverter) {
        this.contentSearchResponseConverter = contentSearchResponseConverter;
    }
}
