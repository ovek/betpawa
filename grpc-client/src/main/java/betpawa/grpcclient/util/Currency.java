package betpawa.grpcclient.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Currency {
    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String GBP = "GBP";
    public static final Set<String> SUPPORTED_CURRENCY = new HashSet(Arrays.asList(USD, EUR, GBP));
}
