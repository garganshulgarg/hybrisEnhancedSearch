/**
 *
 */
package com.custom.enhanced.search.dao.impl;

import com.custom.enhanced.search.dao.ContentSearchDao;
import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collection;


/**
 * @author anshulgarg
 *
 */
public class ContentSearchDaoImpl implements ContentSearchDao
{
	private static final Logger LOG = LoggerFactory.getLogger(ContentSearchDaoImpl.class);
	private static final String PK = "pk";

	private FlexibleSearchService flexibleSearchService;

	@Override
	public Collection<CMSParagraphComponentModel> findParagraphComponentsForContentPage(final ContentPageModel page)
	{
		final StringBuilder query = new StringBuilder("SELECT paragraphs.pk FROM ({{").append("SELECT DISTINCT {p.pk} ")
				.append("FROM {ContentPage as cp ").append("JOIN PageTemplate as pt ON {cp.masterTemplate} = {pt.pk}")
				.append(" JOIN ContentSlotForTemplate as csft ON {csft.pageTemplate} = {pt.pk}")
				.append(
						" JOIN ContentSlot as cs ON {csft.contentSlot} = {cs.pk} JOIN ElementsForSlot as efs ON {cs.pk} = {efs.source}")
				.append(" JOIN CMSParagraphComponent as p ON {p.pk} = {efs.target}} ").append("WHERE {cp.pk} =?")
				.append(ContentSearchDaoImpl.PK).append(" }} UNION {{").append("SELECT DISTINCT {p.pk} ")
				.append("FROM {ContentPage as cp").append(" JOIN ContentSlotForPage as csfp ON {csfp.page} = {cp.pk}")
				.append(" JOIN ContentSlot as cs ON {csfp.contentSlot} = {cs.pk}")
				.append(" JOIN ElementsForSlot as efs ON {cs.pk} = {efs.source}")
				.append(" JOIN CMSParagraphComponent as p ON {p.pk} = {efs.target}} ").append("WHERE {cp.pk} = ?")
				.append(ContentSearchDaoImpl.PK).append("	}}) paragraphs");

		//create a debug message with the query for future debugging in UAT and PRD
		LOG.debug("Query: {} - ContentPage pk: {}", query.toString(), page.getPk());

		final FlexibleSearchQuery fsq = new FlexibleSearchQuery(query.toString());
		fsq.addQueryParameter(ContentSearchDaoImpl.PK, page.getPk());

		return this.flexibleSearchService.<CMSParagraphComponentModel> search(fsq).getResult();
	}

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}
}
