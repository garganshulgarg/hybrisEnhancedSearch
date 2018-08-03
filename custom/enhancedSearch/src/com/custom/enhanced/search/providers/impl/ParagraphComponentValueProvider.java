/**
 *
 */
package com.custom.enhanced.search.providers.impl;

import com.custom.enhanced.search.service.ContentSearchService;
import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;

import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;
import java.util.*;


/**
 * Value provider to index the content of all the paragraph components associated with a ContentPage in a multi-value
 * indexed property.
 *
 * @author anshulgarg
 *
 */
public class ParagraphComponentValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private static final Logger LOG = LoggerFactory.getLogger(ParagraphComponentValueProvider.class);

	private final Map<String, Locale> locales = new HashMap<String, Locale>();

	private FieldNameProvider fieldNameProvider;
	private ContentSearchService contentSearchService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
												 final Object model) throws FieldValueProviderException
	{
		final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
		//check that the object is a content page, if not throw an exception
		if (model instanceof ContentPageModel)
		{
			final ContentPageModel page = (ContentPageModel) model;
			if (indexedProperty.isLocalized())
			{
				final Collection<LanguageModel> languages = indexConfig.getLanguages();
				if (null != languages)
				{
					for (final LanguageModel language : languages)
					{
						fieldValues.addAll(createFieldValue(page, language, indexedProperty));
					}
				}
				else
				{
					LOG.error("No language configured for the current indexConfig.");
				}
			}
			else
			{
				LOG.error(
						"Trying to use a localised value provider (ParagraphComponentValueProvider) for a non localised property {}",
						indexedProperty.getName());
			}
		}
		else
		{
			LOG.error("Error while indexing ParagraphComponents. The object {} is not a ContentPage", model.toString());
		}
		return fieldValues;
	}

	/**
	 * Creates a FieldValue with the content of the paragraph components for page and language.
	 *
	 * @param page
	 * @param language
	 * @param indexedProperty
	 * @return
	 */
	protected List<FieldValue> createFieldValue(final ContentPageModel page, final LanguageModel language,
			final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

		final Collection<CMSParagraphComponentModel> paragraphs = this.contentSearchService
				.findParagraphComponentsForContentPage(page);
		if (null != paragraphs)
		{
			for (final CMSParagraphComponentModel paragraph : paragraphs)
			{
				final String propertyLanguage = language == null ? null : language.getIsocode();
				final Collection<String> fieldNames = this.fieldNameProvider.getFieldNames(indexedProperty, propertyLanguage);
				for (final String fieldName : fieldNames)
				{
					fieldValues.add(new FieldValue(fieldName, paragraph.getContent(this.getLocale(propertyLanguage))));
				}
			}
		}

		return fieldValues;
	}

	/**
	 * Gets the Locale object based on the language iso code. This method guarantees that only one Locale per language is
	 * created.
	 *
	 * @param language
	 * @return the Locale object based on the language iso code
	 */
	private Locale getLocale(final String language)
	{
		Locale locale = null;
		if (this.locales.containsKey(language))
		{
			locale = this.locales.get(language);
		}
		else
		{
			locale = new Locale(language);
			this.locales.put(language, locale);
		}

		return locale;
	}

	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	@Required
	public void setContentSearchService(final ContentSearchService contentSearchService)
	{
		this.contentSearchService = contentSearchService;
	}


}
