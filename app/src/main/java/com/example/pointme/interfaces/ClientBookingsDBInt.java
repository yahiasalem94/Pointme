package com.example.pointme.interfaces;

import com.example.pointme.constants.ServerResult;
import com.example.pointme.models.Booking;

import java.util.ArrayList;

public interface ClientBookingsDBInt {

    void setClientBookings(@ServerResult int serverResult, ArrayList<Booking> bookingsList);
}
