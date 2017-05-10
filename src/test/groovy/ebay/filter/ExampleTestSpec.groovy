package ebay.filter

import ebay.filter.pages.specification.EbayGebSpecification

class ExampleTestSpec extends EbayGebSpecification{

    def "Example Test Case"() {

        given: "I navigate to ebay"
        go EbayGebSpecification.ebayHomeUrl

        when: "enter iPhone into the search field"
        $("input", id:"gh-ac") << "iPhone"
        $("input", id:"gh-btn").click()

        then: "assertions"
        1==1
    }
}
