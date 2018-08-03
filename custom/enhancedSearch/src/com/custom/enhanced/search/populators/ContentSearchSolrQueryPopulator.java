/**
 *
 */
package com.custom.enhanced.search.populators;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.contents.ContentCatalogModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.exceptions.NoValidSolrConfigException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Populates generic attributes like (content catalog, facetSearchConfig, indexedType, query, currency and language)
 * before running solr query.
 *
 * @author anshulgarg
 *
 */
public class ContentSearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE> implements
		Populator<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE>>
{
	private static final Logger LOG = LoggerFactory.getLogger(ContentSearchSolrQueryPopulator.class);

	private BaseSiteService baseSiteService;
	private CMSSiteService cmsSiteService;
	private CatalogVersionService catalogVersionService;
	private CommonI18NService commonI18NService;
	private Converter<SolrFacetSearchConfigModel, FacetSearchConfig> facetSearchConfigConverter;

	@Override
	public void populate(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
					throws ConversionException
	{
		// Setup the SolrSearchRequest
		target.setSearchQueryData(source.getSearchQueryData());

		//set current content catalog version if required
		final Collection<CatalogVersionModel> catalogs = this.getSessionCatalogVersions();
		if (null != catalogs)
		{
			target.setCatalogVersions(new ArrayList<CatalogVersionModel>(catalogs));
		}

		//set facet search config
		try
		{
			final FacetSearchConfig facetSearchConfig = this.getFacetSearchConfig();
			target.setFacetSearchConfig(facetSearchConfig);

			LOG.debug("Searching using the facetSearchConfig: {}", facetSearchConfig != null ? facetSearchConfig.getName() : "none");
		}
		catch (final NoValidSolrConfigException e)
		{
			throw new ConversionException("No valid solrFacetSearchConfig found for the current context", e);
		}

		// We can only search one core so select the indexed type
		final IndexedType indexedType = getIndexedType(target.getFacetSearchConfig());
		target.setIndexedType(indexedType);
		LOG.debug("Searching using the indexed type: {}", indexedType.getIndexName());

		// Create the solr search query for the config and type (this sets-up the default page size and sort order)
		target.setSearchQuery(createSearchQuery(target.getFacetSearchConfig(), target.getIndexedType()));
		target.getSearchQuery().setCatalogVersions(target.getCatalogVersions());
		target.getSearchQuery().setCurrency(this.commonI18NService.getCurrentCurrency().getIsocode());
		target.getSearchQuery().setLanguage(this.commonI18NService.getCurrentLanguage().getIsocode());

		// Set free text query from user input to allow for spell correction and keyword redirect
		target.getSearchQuery().setUserQuery(source.getSearchQueryData().getFreeTextSearch());

		// don't enable spell checker
		target.getSearchQuery().setEnableSpellcheck(false);
	}

	/**
	 * Get all the session catalog versions that belong to content catalogs for the current site.
	 *
	 * @return the list of session catalog versions. Empty ArrayList if none exists.
	 */
	protected Collection<CatalogVersionModel> getSessionCatalogVersions()
	{
		final CMSSiteModel currentSite = this.cmsSiteService.getCurrentSite();
		final List<ContentCatalogModel> contentCatalogs = currentSite.getContentCatalogs();
		final Collection<CatalogVersionModel> sessionCatalogVersions = this.catalogVersionService.getSessionCatalogVersions();

		final Collection<CatalogVersionModel> result = new ArrayList<CatalogVersionModel>();
		for (final CatalogVersionModel sessionCatalogVersion : sessionCatalogVersions)
		{
			if (contentCatalogs.contains(sessionCatalogVersion.getCatalog()))
			{
				result.add(sessionCatalogVersion);
			}
		}
		return result;
	}


	/**
	 * Resolves {@link FacetSearchConfig} base on base site configuration.
	 *
	 * @return {@link FacetSearchConfig} that is converted from {@link SolrFacetSearchConfigModel}
	 * @throws NoValidSolrConfigException
	 */
	protected FacetSearchConfig getFacetSearchConfig() throws NoValidSolrConfigException
	{
		FacetSearchConfig facetSearchConfig = null;
		if (null != this.baseSiteService.getCurrentBaseSite()
				&& null != this.baseSiteService.getCurrentBaseSite().getSolrContentFacetSearchConfiguration())
		{
			facetSearchConfig = this.facetSearchConfigConverter
					.convert(this.baseSiteService.getCurrentBaseSite().getSolrContentFacetSearchConfiguration());
		}
		else
		{
			final String currentBaseSite = null != this.baseSiteService.getCurrentBaseSite()
					? this.baseSiteService.getCurrentBaseSite().getName() : null;
			throw new NoValidSolrConfigException(
					"Solr Content facet search configuration not found for current base site " + currentBaseSite);
		}
		return facetSearchConfig;
	}

	/**
	 * Gets the indexed type to be used for the content search. In case of multiple indexed types for the same
	 * FacetSearchConfig, the first one is used. Functionality duplicated from hybris OOTB.
	 *
	 * @param config
	 * @return The indexed type to be used for the content search.
	 */
	protected IndexedType getIndexedType(final FacetSearchConfig config)
	{
		final IndexConfig indexConfig = config.getIndexConfig();

		// Strategy for working out which of the available indexed types to use
		final Collection<IndexedType> indexedTypes = indexConfig.getIndexedTypes().values();
		if (indexedTypes != null && !indexedTypes.isEmpty())
		{
			// When there are multiple - select the first
			return indexedTypes.iterator().next();
		}

		// No indexed types
		return null;
	}

	/**
	 * Creates the SearchQuery based on configuration and indexed type to be used.
	 *
	 * @param config
	 * @param indexedType
	 * @return the SearchQuery based on configuration and indexed type to be used.
	 */
	protected SearchQuery createSearchQuery(final FacetSearchConfig config, final IndexedType indexedType)
	{
		return new SearchQuery(config, indexedType);
	}

	public BaseSiteService getBaseSiteService()
	{
		return this.baseSiteService;
	}

	@Required
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	@Required
	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	public Converter<SolrFacetSearchConfigModel, FacetSearchConfig> getFacetSearchConfigConverter()
	{
		return facetSearchConfigConverter;
	}

	@Required
	public void setFacetSearchConfigConverter(
			final Converter<SolrFacetSearchConfigModel, FacetSearchConfig> facetSearchConfigConverter)
	{
		this.facetSearchConfigConverter = facetSearchConfigConverter;
	}

	@Required
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}
}
