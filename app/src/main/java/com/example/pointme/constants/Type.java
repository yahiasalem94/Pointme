package com.example.pointme.constants;

import androidx.annotation.IntDef;

@IntDef({Type.EVENT, Type.APPOINTMENT})
public @interface Type {
    int EVENT = 1;
    int APPOINTMENT = 2;
    int WORKSHOP = 3;
}
