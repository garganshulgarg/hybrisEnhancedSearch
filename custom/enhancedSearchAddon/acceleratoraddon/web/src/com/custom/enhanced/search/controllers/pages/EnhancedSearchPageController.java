package com.custom.enhanced.search.controllers.pages;


import com.custom.enhanced.search.data.ContentData;
import com.custom.enhanced.search.facades.ContentSearchFacade;
import com.google.common.collect.Lists;
import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.search.data.AutocompleteResultData;
import de.hybris.platform.yacceleratorstorefront.controllers.pages.SearchPageController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class EnhancedSearchPageController extends SearchPageController {

    @Resource(name = "cmsComponentService")
    private CMSComponentService cmsComponentService;

    @Resource(name = "contentSearchFacade")
    private ContentSearchFacade contentSearchFacade;


    @Override
    public String textSearch(@RequestParam(value = "text", defaultValue = "") final String searchText,
                             final HttpServletRequest request, final Model model) throws CMSItemNotFoundException{

        String viewURL = super.textSearch(searchText, request, model);
        Collection<ContentData> contentSearchData = contentSearchFacade.contentTextSearch(searchText);
        if(CollectionUtils.isNotEmpty(contentSearchData)){
            model.addAttribute("contentSearchData", contentSearchData);
        }
        return viewURL;
    }

    @Override
    public String refineSearch(@RequestParam("q") final String searchQuery,
                               @RequestParam(value = "page", defaultValue = "0") final int page,
                               @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
                               @RequestParam(value = "sort", required = false) final String sortCode,
                               @RequestParam(value = "text", required = false) final String searchText, final HttpServletRequest request,
                               final Model model) throws CMSItemNotFoundException
    {
        String viewURL = super.refineSearch(searchQuery, page, showMode, sortCode, searchText, request, model);
        Collection<ContentData> contentSearchData = contentSearchFacade.contentTextSearch(searchText);
        if(CollectionUtils.isNotEmpty(contentSearchData)){
            model.addAttribute("contentSearchData", contentSearchData);
        }
        return viewURL;
    }

    @Override
    public AutocompleteResultData getAutocompleteSuggestions(@PathVariable final String componentUid,
                                                             @RequestParam("term") final String term) throws CMSItemNotFoundException {
        AutocompleteResultData autocompleteResultData = super.getAutocompleteSuggestions(componentUid, term);
        final SearchBoxComponentModel component = (SearchBoxComponentModel) cmsComponentService.getSimpleCMSComponent(componentUid);
        if (component.isDisplayContent()) {
            autocompleteResultData.setContent(subList(Lists.newArrayList(contentSearchFacade.contentTextSearch(term)), component.getMaxContent()));
        }


        return autocompleteResultData;
    }

}