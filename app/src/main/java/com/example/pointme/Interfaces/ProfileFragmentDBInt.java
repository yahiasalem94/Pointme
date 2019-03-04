package com.example.pointme.Interfaces;

import com.example.pointme.models.Event;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;

public interface ProfileFragmentDBInt {

    void setProfile(ProfileInfo profile, ArrayList<Event> eventsList);
}
