package com.example.pointme.interfaces;

import com.example.pointme.constants.ServerResult;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;

import java.util.ArrayList;

public interface EventsFragmentDBInt {

    void setSPEventsAndAppointments(@ServerResult int serverResult, ArrayList<Event> eventsList, ArrayList<Appointment> appointmentsList);
}
