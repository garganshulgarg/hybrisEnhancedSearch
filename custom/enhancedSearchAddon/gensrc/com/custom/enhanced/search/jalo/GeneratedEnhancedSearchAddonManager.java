/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 1 Aug, 2018 4:37:00 PM                      ---
 * ----------------------------------------------------------------
 */
package com.custom.enhanced.search.jalo;

import com.custom.enhanced.search.constants.EnhancedSearchAddonConstants;
import de.hybris.platform.acceleratorcms.jalo.components.SearchBoxComponent;
import de.hybris.platform.basecommerce.jalo.site.BaseSite;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.cms2.jalo.pages.AbstractPage;
import de.hybris.platform.cms2.jalo.pages.ContentPage;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.solrfacetsearch.jalo.config.SolrFacetSearchConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>EnhancedSearchAddonManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedEnhancedSearchAddonManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("searchType", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.cms2.jalo.pages.ContentPage", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("displayContent", AttributeMode.INITIAL);
		tmp.put("maxContent", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.acceleratorcms.jalo.components.SearchBoxComponent", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("solrContentFacetSearchConfiguration", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.basecommerce.jalo.site.BaseSite", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContent</code> attribute.
	 * @return the displayContent - Determines if content search results are displayed in the component.
	 */
	public Boolean isDisplayContent(final SessionContext ctx, final SearchBoxComponent item)
	{
		return (Boolean)item.getProperty( ctx, EnhancedSearchAddonConstants.Attributes.SearchBoxComponent.DISPLAYCONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContent</code> attribute.
	 * @return the displayContent - Determines if content search results are displayed in the component.
	 */
	public Boolean isDisplayContent(final SearchBoxComponent item)
	{
		return isDisplayContent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContent</code> attribute. 
	 * @return the displayContent - Determines if content search results are displayed in the component.
	 */
	public boolean isDisplayContentAsPrimitive(final SessionContext ctx, final SearchBoxComponent item)
	{
		Boolean value = isDisplayContent( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContent</code> attribute. 
	 * @return the displayContent - Determines if content search results are displayed in the component.
	 */
	public boolean isDisplayContentAsPrimitive(final SearchBoxComponent item)
	{
		return isDisplayContentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContent</code> attribute. 
	 * @param value the displayContent - Determines if content search results are displayed in the component.
	 */
	public void setDisplayContent(final SessionContext ctx, final SearchBoxComponent item, final Boolean value)
	{
		item.setProperty(ctx, EnhancedSearchAddonConstants.Attributes.SearchBoxComponent.DISPLAYCONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContent</code> attribute. 
	 * @param value the displayContent - Determines if content search results are displayed in the component.
	 */
	public void setDisplayContent(final SearchBoxComponent item, final Boolean value)
	{
		setDisplayContent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContent</code> attribute. 
	 * @param value the displayContent - Determines if content search results are displayed in the component.
	 */
	public void setDisplayContent(final SessionContext ctx, final SearchBoxComponent item, final boolean value)
	{
		setDisplayContent( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContent</code> attribute. 
	 * @param value the displayContent - Determines if content search results are displayed in the component.
	 */
	public void setDisplayContent(final SearchBoxComponent item, final boolean value)
	{
		setDisplayContent( getSession().getSessionContext(), item, value );
	}
	
	@Override
	public String getName()
	{
		return EnhancedSearchAddonConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContent</code> attribute.
	 * @return the maxContent - Determines the max number of content search results to display in the component.
	 */
	public Integer getMaxContent(final SessionContext ctx, final SearchBoxComponent item)
	{
		return (Integer)item.getProperty( ctx, EnhancedSearchAddonConstants.Attributes.SearchBoxComponent.MAXCONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContent</code> attribute.
	 * @return the maxContent - Determines the max number of content search results to display in the component.
	 */
	public Integer getMaxContent(final SearchBoxComponent item)
	{
		return getMaxContent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContent</code> attribute. 
	 * @return the maxContent - Determines the max number of content search results to display in the component.
	 */
	public int getMaxContentAsPrimitive(final SessionContext ctx, final SearchBoxComponent item)
	{
		Integer value = getMaxContent( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContent</code> attribute. 
	 * @return the maxContent - Determines the max number of content search results to display in the component.
	 */
	public int getMaxContentAsPrimitive(final SearchBoxComponent item)
	{
		return getMaxContentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContent</code> attribute. 
	 * @param value the maxContent - Determines the max number of content search results to display in the component.
	 */
	public void setMaxContent(final SessionContext ctx, final SearchBoxComponent item, final Integer value)
	{
		item.setProperty(ctx, EnhancedSearchAddonConstants.Attributes.SearchBoxComponent.MAXCONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContent</code> attribute. 
	 * @param value the maxContent - Determines the max number of content search results to display in the component.
	 */
	public void setMaxContent(final SearchBoxComponent item, final Integer value)
	{
		setMaxContent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContent</code> attribute. 
	 * @param value the maxContent - Determines the max number of content search results to display in the component.
	 */
	public void setMaxContent(final SessionContext ctx, final SearchBoxComponent item, final int value)
	{
		setMaxContent( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContent</code> attribute. 
	 * @param value the maxContent - Determines the max number of content search results to display in the component.
	 */
	public void setMaxContent(final SearchBoxComponent item, final int value)
	{
		setMaxContent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.searchType</code> attribute.
	 * @return the searchType
	 */
	public EnumerationValue getSearchType(final SessionContext ctx, final ContentPage item)
	{
		return (EnumerationValue)item.getProperty( ctx, EnhancedSearchAddonConstants.Attributes.ContentPage.SEARCHTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.searchType</code> attribute.
	 * @return the searchType
	 */
	public EnumerationValue getSearchType(final ContentPage item)
	{
		return getSearchType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.searchType</code> attribute. 
	 * @param value the searchType
	 */
	public void setSearchType(final SessionContext ctx, final ContentPage item, final EnumerationValue value)
	{
		item.setProperty(ctx, EnhancedSearchAddonConstants.Attributes.ContentPage.SEARCHTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.searchType</code> attribute. 
	 * @param value the searchType
	 */
	public void setSearchType(final ContentPage item, final EnumerationValue value)
	{
		setSearchType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.solrContentFacetSearchConfiguration</code> attribute.
	 * @return the solrContentFacetSearchConfiguration - Solr content search configuration for this site.
	 */
	public SolrFacetSearchConfig getSolrContentFacetSearchConfiguration(final SessionContext ctx, final BaseSite item)
	{
		return (SolrFacetSearchConfig)item.getProperty( ctx, EnhancedSearchAddonConstants.Attributes.BaseSite.SOLRCONTENTFACETSEARCHCONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.solrContentFacetSearchConfiguration</code> attribute.
	 * @return the solrContentFacetSearchConfiguration - Solr content search configuration for this site.
	 */
	public SolrFacetSearchConfig getSolrContentFacetSearchConfiguration(final BaseSite item)
	{
		return getSolrContentFacetSearchConfiguration( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.solrContentFacetSearchConfiguration</code> attribute. 
	 * @param value the solrContentFacetSearchConfiguration - Solr content search configuration for this site.
	 */
	public void setSolrContentFacetSearchConfiguration(final SessionContext ctx, final BaseSite item, final SolrFacetSearchConfig value)
	{
		item.setProperty(ctx, EnhancedSearchAddonConstants.Attributes.BaseSite.SOLRCONTENTFACETSEARCHCONFIGURATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.solrContentFacetSearchConfiguration</code> attribute. 
	 * @param value the solrContentFacetSearchConfiguration - Solr content search configuration for this site.
	 */
	public void setSolrContentFacetSearchConfiguration(final BaseSite item, final SolrFacetSearchConfig value)
	{
		setSolrContentFacetSearchConfiguration( getSession().getSessionContext(), item, value );
	}
	
}
