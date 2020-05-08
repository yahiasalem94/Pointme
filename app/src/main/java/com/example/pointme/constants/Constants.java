package com.example.pointme.constants;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public static final int EVENTTYPE = 1;
    public static final int APPOINTMENTTYPE = 2;

    /* Firestore Collections */
    public static final String USERS_NODE = "Users";
    public static final String APP_DATA_NODE = "AppData";
    public static final String SERVICE_PROVIDERS = "ServiceProviders";
    public static final String APPOINTMENTS = "Appointments";
    public static final String EVENTS = "Events";
    public static final String BOOKINGS = "Bookings";

    /* Firestore Documents */
    public static final String SERVICES = "Services";

    /* Firestore Queries */
    public static final String SERVICE = "service";
    public static final String UID = "spID";
    public static final String CRID = "crID";

    /* Cloud Fuctions */
    public static final String GET_BOOKING_SLOTS = "getBookingSlots";


    public static final String FAVORITES = "Favorites";
    public static final String APPOINTMENT_SCHEDULE = "AppointmentSchedule";
    public static final String SCHEDULE_DB = "ScheduleDB";
    public static final String SCHEDULE_WB = "ScheduleWB";
    public static final String BOOKER_SCHEDULE = "BookerSchedule";
    public static final String UNICODE_END_CHARACTER = "\uf8ff";
    public static final String DATES = "Dates";
    public static final String EVENTSPATH = "/Events/";
    public static final String APPOINTMENTSPATH = "/Appointments/";
    public static final String BOOKINGSPATH = "/Bookings/";
    public static final String DATESPATH = "/Dates/";
    public static final String JPG_EXT = ".jpg";

    /* FB Permissions */
    public static final String FB_EMAIL = "email";
    public static final String FB_PROFILE = "public_profile";
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EVENTTYPE, APPOINTMENTTYPE})
    public @interface ScheduleType { }

}
