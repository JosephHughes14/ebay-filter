package ebay.filter.pages.modules

import geb.Module

class ResultItemModule extends Module {

    static final String POUNDSYMBOL = "\u00A3"

    static content = {
        title                        { $("h3", class:"lvtitle") }
        price                        { $("li", class:"lvprice") }
        format                       { $("li", class: "lvformat") }
        shippingFee(required: false) { $("li", class:"lvshipping").find("span", class:"fee").text() }
    }

    Integer numberOfBids() {
        format.text().replace(" bids", "").toInteger()
    }

    String formatPrice(String elementText) {
        elementText
                .replace(POUNDSYMBOL, "")
                .replace(",", "")
                .replace("+", "")
                .replace("postage", "")
                .replaceAll(" ", "")
    }

    BigDecimal listingPrice() {
        String priceText = formatPrice(price.find("span").text())
        new BigDecimal(priceText)
    }

    BigDecimal listingPriceAndPostage() {
        BigDecimal postage = freePostage()? new BigDecimal(0) : new BigDecimal(formatPrice(shippingFee))
        new BigDecimal(listingPrice() + postage)
    }

    Boolean singlePrice() {
        price.find("span").collect().size() == 1
    }

    Boolean freePostage() {
        !shippingFee
    }
}
