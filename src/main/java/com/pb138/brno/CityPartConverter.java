package com.pb138.brno;

import org.springframework.stereotype.Service;

@Service
public class CityPartConverter {
    public int regionToInt(String region) {
        switch (region) {
            case "brno":
                return 10;
            case "str":
                return 1;
            case "zbv":
                return 2;
            case "svr":
                return 3;
            case "zid":
                return 4;
            case "krp":
                return 5;
            case "kom":
                return 6;
            case "vys":
                return 7;
            case "bys":
                return 8;
            case "dpv":
                return 9;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Converts number to city part string for comparison
     *
     * @param cityPart
     * @return string city part
     */
    public String numberToCityPart(int cityPart) {
        switch (cityPart) {
            case 1:
                return "OOP BRNO - STŘED";
            case 2:
                return "OOP BRNO - ŽABOVŘESKY";
            case 3:
                return "OOP BRNO - SEVER";
            case 4:
                return "OOP BRNO - ŽIDENICE";
            case 5:
                return "OOP BRNO - KRÁLOVO POLE";
            case 6:
                return "OOP BRNO - KOMÁROV";
            case 7:
                return "OOP BRNO - VÝSTAVIŠTĚ";
            case 8:
                return "OOP BRNO - BYSTRC";
            case 9:
                return "OŽP A DOPROVODU VLAKŮ";
            case 10:
                return "Celé Brno";
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Converts city part string to number for comparison
     *
     * @param cityPart
     * @return int city part
     */
    public int cityPartToNumber(String cityPart) {
        switch (cityPart) {
            case "OOP BRNO - STŘED":
                return 1;
            case "OOP BRNO - ŽABOVŘESKY":
                return 2;
            case "OOP BRNO - SEVER":
                return 3;
            case "OOP BRNO - ŽIDENICE":
                return 4;
            case "OOP BRNO - KRÁLOVO POLE":
                return 5;
            case "OOP BRNO - KOMÁROV":
                return 6;
            case "OOP BRNO - VÝSTAVIŠTĚ":
                return 7;
            case "OOP BRNO - BYSTRC":
                return 8;
            case "OŽP A DOPROVODU VLAKŮ":
                return 9;
            case "Celé Brno":
                return 10;
            default:
                throw new IllegalArgumentException();
        }
    }
}
