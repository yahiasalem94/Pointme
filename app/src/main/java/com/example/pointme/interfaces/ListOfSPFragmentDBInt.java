package com.example.pointme.interfaces;

import com.example.pointme.constants.ServerResult;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;

public interface ListOfSPFragmentDBInt {

    void setSPList(ArrayList<String> sPList);

    void setSPList(@ServerResult int serverResult, ArrayList<ProfileInfo> profilesList);
}
