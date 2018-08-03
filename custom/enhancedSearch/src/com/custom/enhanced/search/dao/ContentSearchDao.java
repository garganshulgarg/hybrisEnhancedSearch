/**
 *
 */
package com.custom.enhanced.search.dao;

import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;

import java.util.Collection;


/**
 * DAO class for the solrfacetsearch in enhancedSearch extension.
 *
 * @author anshulgarg
 *
 */
public interface ContentSearchDao
{

	/**
	 * Looks for CMSParagraphComponents associated with the content page. The CMSParagraphComponets can be associated
	 * with the page by the page or by the page template.
	 *
	 * @param page
	 * @return The list of CMSParagraphComponents associated with the content page.
	 */
	Collection<CMSParagraphComponentModel> findParagraphComponentsForContentPage(ContentPageModel page);
}
