package com.example.alex.contactstest;

/**
 * Created by alex on 9/18/15.
 */
public class CourseTimeStore {
    boolean sunday;
    boolean monday;
    boolean tuesday;
    boolean wednesday;
    boolean thursday;
    boolean friday;
    boolean saturday;

    int startHour;
    int startMin;
    int endHour;
    int endMin;

    public void enableDay(int dayNum) {
        switch (dayNum) {
            case 1:
                sunday = true;
                break;
            case 2:
                monday = true;
                break;
            case 3:
                tuesday = true;
                break;
            case 4:
                wednesday = true;
                break;
            case 5:
                thursday = true;
                break;
            case 6:
                friday = true;
                break;
            case 7:
                saturday = true;
                break;
        }
    }

    public void disableDay(int dayNum) {
        switch (dayNum) {
            case 1:
                sunday = false;
                break;
            case 2:
                monday = false;
                break;
            case 3:
                tuesday = false;
                break;
            case 4:
                wednesday = false;
                break;
            case 5:
                thursday = false;
                break;
            case 6:
                friday = false;
                break;
            case 7:
                saturday = false;
                break;
        }
    }

    public void setStartTime(int hour, int min) {
        startHour = hour;
        startMin = min;
    }

    public void setEndTime(int hour, int min) {
        endHour = hour;
        endMin = min;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public boolean getSunday() {
        return sunday;
    }

    public boolean getMonday() {
        return monday;
    }

    public boolean getTuesday() {
        return tuesday;
    }

    public boolean getWednesday() {
        return wednesday;
    }

    public boolean getThursday() {
        return thursday;
    }

    public boolean getFriday() {
        return friday;
    }

    public boolean getSaturday() {
        return saturday;
    }
}
