package com.tgrajkowski;

import org.junit.Test;

public class SearchLocation {

    @Test
    public void justTest() {
        String locationAddress = "{\"Response\":{\"MetaInfo\":{\"Timestamp\":\"2018-04-16T12:40:43.239+0000\"},\"View\":[{\"_type\":\"SearchResultsViewType\",\"ViewId\":0,\"Result\":[{\"Relevance\":1.0,\"MatchLevel\":\"city\",\"MatchQuality\":{\"City\":1.0},\"Location\":{\"LocationId\":\"NT_wj8w2kE-4kF0gZkDnl4.vB\",\"LocationType\":\"point\",\"DisplayPosition\":{\"Latitude\":53.01276,\"Longitude\":18.61278},\"NavigationPosition\":[{\"Latitude\":53.01276,\"Longitude\":18.61278}],\"MapView\":{\"TopLeft\":{\"Latitude\":53.06553,\"Longitude\":18.46216},\"BottomRight\":{\"Latitude\":52.96041,\"Longitude\":18.74022}},\"Address\":{\"Label\":\"Toruń, Woj. Kujawsko-Pomorskie, Polska\",\"Country\":\"POL\",\"State\":\"Woj. Kujawsko-Pomorskie\",\"County\":\"Toruń\",\"City\":\"Toruń\",\"PostalCode\":\"87-100\",\"AdditionalData\":[{\"value\":\"Polska\",\"key\":\"CountryName\"},{\"value\":\"Woj. Kujawsko-Pomorskie\",\"key\":\"StateName\"},{\"value\":\"Toruń\",\"key\":\"CountyName\"}]}}}]}]}}";

        int labelStart = locationAddress.indexOf("Label");
        int labelEnd = locationAddress.indexOf("\"AdditionalData", labelStart+3);
        System.out.println("start: "+labelStart);
        System.out.println("stop: "+labelEnd);
        String label = locationAddress.substring(labelStart, labelEnd-1);
        label = label.replace("Label\":", "");
        System.out.println(label);
    }
}
