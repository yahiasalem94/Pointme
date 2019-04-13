package com.example.pointme.constants;

import android.support.annotation.IntDef;

@IntDef({DBWriteType.FAVORITES, DBWriteType.BOOKINGS, DBWriteType.EVENTS, DBWriteType.APPOINTMENTS})
public @interface DBWriteType {
    int FAVORITES = 0;
    int BOOKINGS = 1;
    int EVENTS = 2;
    int APPOINTMENTS = 3;
}
