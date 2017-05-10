package ebay.filter

import ebay.filter.pages.EbayHomePage
import ebay.filter.specification.EbayGebSpecification

class ExampleTestSpec extends EbayGebSpecification{

    def "Example Test Case"() {

        given: "I navigate to ebay"
        to EbayHomePage

        when: "enter iPhone into the search field"
        search.searchFor "iPhone"

        then: "assertions"
        1==1
    }
}
