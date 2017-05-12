package ebay.filter.pages

import ebay.filter.domain.SortOptions
import ebay.filter.pages.modules.SearchResultListingModule
import ebay.filter.pages.modules.SortResultsModule
import geb.Page
import geb.navigator.Navigator

class SearchResultsPage extends Page {

    static at = {
        waitFor { $("div", id:"cbrt") }
    }

    static content = {
        sort                      { module SortResultsModule }
        listingFilterContainer    { $("div", class:"pnl-b") }
        searchResultListings      { $("li", class:"lvresult").collect { it.module(SearchResultListingModule) } }
    }

    void sortBy(Integer sortOptionValue) {
        sort.changeSortPreference(sortOptionValue)
    }

    void filterBy(String listingType) {
        listingFilterContainer.find(class:"tgl_button").find { it.text().contains(listingType) }.click()
    }

    List<Navigator> singlePriceListings() {
        //TODO: Document this hack
        searchResultListings.findAll { listing ->
            listing.singlePrice()
        }
    }

    List<BigDecimal> resultPrices() {
        singlePriceListings().collect { listing ->
            listing.listingPrice()
        }
    }

    List<BigDecimal> resultPriceAndPostages() {
        singlePriceListings().collect { listing ->
            listing.listingPriceAndPostage()
        }
    }

    Boolean sortedAscendingPrice() {
        resultPrices() == resultPrices().sort()
    }

    Boolean sortedAscendingPriceAndPostage() {
        resultPriceAndPostages() == resultPriceAndPostages().sort()
    }

    Boolean sortedDecendingPrice() {
        resultPrices() == resultPrices().sort().reverse()
    }

    Boolean sortedDecendingPriceAndPostage() {
        resultPriceAndPostages() == resultPriceAndPostages().sort().reverse()
    }

    Boolean isSortedBy(Integer sortOptionValue) {
        switch (sortOptionValue) {
            case SortOptions.LOW_PRICE:
                sortedAscendingPrice()
                break
            case SortOptions.HIGH_PRICE:
                sortedDecendingPrice()
                break
            case SortOptions.LOW_PRICE_AND_PP:
                sortedAscendingPriceAndPostage()
                break
            case SortOptions.HIGH_PRICE_AND_PP:
                sortedDecendingPriceAndPostage()
                break
            default:
                false
        }
    }
}
