package ebay.filter

import ebay.filter.pages.EbayHomePage
import ebay.filter.pages.SearchResultsPage
import ebay.filter.domain.ListingFilterOptions
import ebay.filter.domain.SortOptions
import ebay.filter.specification.EbayGebSpecification
import spock.lang.Unroll


class SearchFilterSpec extends EbayGebSpecification {

    @Unroll
    def "Filtering search results by #listingType and sorting by #sortBy should return results in order"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for an item"
        search.searchFor "iPhone"

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I filter the results by #listingType and sort by #sortType"
        filterBy(listingType)
        sortBy(sortType)

        then: "The listings are sorted correctly"
        resultItems.size() > 1
        isSortedBy(sortType)

        where:
        listingType                        | sortType
        ListingFilterOptions.BUY_IT_NOW    | SortOptions.LOW_PRICE
        ListingFilterOptions.AUCTION       | SortOptions.LOW_PRICE
        ListingFilterOptions.ALL_LISTINGS  | SortOptions.LOW_PRICE
        ListingFilterOptions.BUY_IT_NOW    | SortOptions.HIGH_PRICE
        ListingFilterOptions.AUCTION       | SortOptions.HIGH_PRICE
        ListingFilterOptions.ALL_LISTINGS  | SortOptions.HIGH_PRICE
        ListingFilterOptions.BUY_IT_NOW    | SortOptions.LOW_PRICE_AND_PP
        ListingFilterOptions.AUCTION       | SortOptions.LOW_PRICE_AND_PP
        ListingFilterOptions.ALL_LISTINGS  | SortOptions.LOW_PRICE_AND_PP
        ListingFilterOptions.BUY_IT_NOW    | SortOptions.HIGH_PRICE_AND_PP
        ListingFilterOptions.AUCTION       | SortOptions.HIGH_PRICE_AND_PP
        ListingFilterOptions.ALL_LISTINGS  | SortOptions.HIGH_PRICE_AND_PP
    }

    def "Sorting by newest listing, in Auctions, for a popular item should yeild zero bid items"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for an item"
        search.searchFor "iPhone"

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I filter the results by #listingType and sort by #sortType"
        filterBy(ListingFilterOptions.AUCTION)
        sortBy(SortOptions.NEWEST)

        then: "The first item in the results should have zeo bids"
        resultItems.first().numberOfBids() == 0
    }
}
