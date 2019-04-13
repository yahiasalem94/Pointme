package com.example.pointme.interfaces;

import com.example.pointme.constants.ServerResult;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;

public interface ProfileFragmentDBInt {

    void setProfile(ProfileInfo profile, ArrayList<Event> eventsList);

    void setSPEventsAndAppointments(@ServerResult int serverResult, ArrayList<Event> eventsList, ArrayList<Appointment> appointmentsList);
}
