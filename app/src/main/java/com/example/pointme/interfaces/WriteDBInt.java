package com.example.pointme.interfaces;

import com.example.pointme.constants.DBWriteType;
import com.example.pointme.constants.ServerResult;

public interface WriteDBInt {

    void writeToDBResult(@ServerResult int serverResult, @DBWriteType int writeType);
}
