package com.organization.employee.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class HtmlUtils {
    public static String sanitize(String input) {
        return Jsoup.clean(input, Safelist.basic());
    }
}