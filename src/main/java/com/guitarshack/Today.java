package com.guitarshack;

import java.util.Calendar;
import java.util.Date;

public class Today implements DateProvider {
    @Override
    public Date date() {
        return Calendar.getInstance().getTime();
    }
}