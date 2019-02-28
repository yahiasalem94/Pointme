package com.example.pointme.Interfaces;

import com.example.pointme.models.Event;
import com.example.pointme.models.Profile;

import java.util.ArrayList;

public interface ProfileFragmentDBInt {

    void setProfile(Profile profile, ArrayList<Event> eventsList);
}
