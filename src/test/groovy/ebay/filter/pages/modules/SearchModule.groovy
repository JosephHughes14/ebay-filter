package ebay.filter.pages.modules

import geb.Module

class SearchModule extends Module {

    static content = {
        searchTextBox { $("input", id:"gh-ac") }
        searchButton  { $("input", id:"gh-btn") }
    }

    void searchFor(String input) {
        searchTextBox << input
        searchButton.click()
    }

}
