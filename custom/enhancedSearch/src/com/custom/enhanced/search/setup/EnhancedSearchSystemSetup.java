/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.custom.enhanced.search.setup;

import static com.custom.enhanced.search.constants.EnhancedSearchConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.custom.enhanced.search.constants.EnhancedSearchConstants;
import com.custom.enhanced.search.service.EnhancedSearchService;


@SystemSetup(extension = EnhancedSearchConstants.EXTENSIONNAME)
public class EnhancedSearchSystemSetup
{
	private final EnhancedSearchService enhancedSearchService;

	public EnhancedSearchSystemSetup(final EnhancedSearchService enhancedSearchService)
	{
		this.enhancedSearchService = enhancedSearchService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		enhancedSearchService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return EnhancedSearchSystemSetup.class.getResourceAsStream("/enhancedSearch/sap-hybris-platform.png");
	}
}
