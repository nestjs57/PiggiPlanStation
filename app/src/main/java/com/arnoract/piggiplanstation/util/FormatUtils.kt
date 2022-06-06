package com.arnoract.piggiplanstation.util

import java.math.BigDecimal
import java.text.DecimalFormat

object FormatUtils {
    private val WITHOUT_DECIMAL_FORMAT = DecimalFormat("#,###")
    private val DECIMAL_FORMAT = DecimalFormat("#,##0.0#")
    private val TWO_DECIMAL_PLACES_FORMAT = DecimalFormat("#,##0.00")
    private val WITH_OR_WITHOUT_DECIMAL_FORMAT = DecimalFormat("#,###.##")
    private val PRICE_SIGN_DECIMAL_FORMAT = DecimalFormat("฿#,##0.00;฿-#")
    private val PRICE_SIGN_SUFFIX_DECIMAL_FORMAT = DecimalFormat("#,##0.00฿;฿-#฿")
    private val SIGN_DECIMAL_FORMAT = DecimalFormat("+#,##0.00;-#")
    private val TWO_DECIMAL_PLACES_WITHOUT_COMMA_FORMAT = DecimalFormat("#.00")
    private val OPTIONAL_TWO_DECIMAL_PLACES_WITHOUT_COMMA_FORMAT = DecimalFormat("#.##")

    fun formatNumberWithoutDecimal(number: Double): String = WITHOUT_DECIMAL_FORMAT.format(
        BigDecimal(number)
    )

    fun formatNumberWithoutDecimal(number: Int): String = WITHOUT_DECIMAL_FORMAT.format(
        BigDecimal(
            number
        )
    )

    fun formatNumberWithDecimal(number: Double): String = DECIMAL_FORMAT.format(BigDecimal(number))

    fun formatNumberWithTwoDecimalPlaces(number: Double): String = TWO_DECIMAL_PLACES_FORMAT.format(
        BigDecimal(number)
    )

    fun formatNumberWithOrWithOutDecimal(number: Double): String =
        WITH_OR_WITHOUT_DECIMAL_FORMAT.format(
            BigDecimal(number)
        )

    fun formatPriceWithPrefix(price: Double): String = PRICE_SIGN_DECIMAL_FORMAT.format(
        BigDecimal(
            price
        )
    )

    fun formatPriceWithSuffix(price: Double): String = PRICE_SIGN_SUFFIX_DECIMAL_FORMAT.format(
        BigDecimal(price)
    )

    fun formatNumberWithSign(number: Double): String =
        SIGN_DECIMAL_FORMAT.format(BigDecimal(number))

    fun formatTwoDecimalPlaces(number: Double): String =
        TWO_DECIMAL_PLACES_WITHOUT_COMMA_FORMAT.format(
            BigDecimal(number)
        )

    fun formatOptionalTwoDecimalPlaces(number: Double): String =
        OPTIONAL_TWO_DECIMAL_PLACES_WITHOUT_COMMA_FORMAT.format(
            BigDecimal(number)
        )
}
