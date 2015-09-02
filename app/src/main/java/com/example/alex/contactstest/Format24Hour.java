package com.example.alex.contactstest;

import android.content.Context;
import android.text.format.DateFormat;

/**
 * Created by alex on 8/29/15. Add new header later.
 */
public class Format24Hour {
    public static String format(int hour, int min, Context context) {
        if (!DateFormat.is24HourFormat(context)) {
            if (hour == 12) {
                if (min < 10) {
                    return ("12:0" + min + " PM");
                } else {
                    return ("12:" + min + " PM");
                }
            }
            else if (hour == 0) {
                if (min < 10) {
                    return ("12:0" + min + " AM");
                } else {
                    return ("12:" + min + " AM");
                }
            }
            else if (hour > 12) {
                if (min < 10) {
                    return ((hour - 12) + ":0" + min + " PM");
                } else {
                    return ((hour - 12) + ":" + min + " PM");
                }
            }
            else {
                if (min < 10) {
                    return (hour + ":0" + min + " AM");
                } else {
                    return (hour + ":" + min + " AM");
                }
            }
        }
        else {
            if (min < 10) {
                return (hour + ":0" + min);
            } else {
                return (hour + ":" + min);
            }
        }
    }
}
