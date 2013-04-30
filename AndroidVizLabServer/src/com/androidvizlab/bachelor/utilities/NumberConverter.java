package com.androidvizlab.bachelor.utilities;

/**
 * Helper class: Converts string values to integer,
 * double and long;
 * 
 * @author The Hive
 */
public class NumberConverter {
    
    private static int intValue = -1;
    private static double doubleValue = -1.0;
    private static long longValue = -1L;

    /**
     * Helper method: converts a string to an integer.
     * raises an exception if the string value is unparseable
     * @param str the string value to be converted to integer.
     * @param defValue default value to be returned if the given 
     * string could not be parsed. 
     * 
     * @return returns the converted string value as an integer.
     */
    public static int converToInt(String str, int defValue)
    {	
        try
        {
            intValue = Integer.parseInt(str);
        }
        catch(NumberFormatException ne)
        {
            intValue = defValue;
        }

        return intValue; 
    }

    /**
     * Helper method: converts a string to an double.
     * raises an exception if the string value is unparseable
     * @param str the string value to be converted to double.
     * @param defValue default value to be returned if the given 
     * string could not be parsed.
     * @return returns the converted string value as an double.
     */
    public static double convertToDouble(String str, double defValue)
    {
        try
        {
            doubleValue = Double.parseDouble(str);
        }
        catch(NumberFormatException ne)
        {
            doubleValue = defValue;
        }

        return doubleValue;
    }

    /**
     * Helper method: converts a string to an long.
     * raises an exception if the string value is unparseable
     * @param str the string value to be converted to long.
     * @param defValue default value to be returned if the given 
     * string could not be parsed.
     * @return returns the converted string value as an long.
     */
    public static long convertToLong(String str, long defValue)
    {
        try
        {
            longValue = Long.parseLong(str);
        }
        catch(NumberFormatException ne)
        {
            longValue = defValue;
        }	

        return longValue;
    }
}
