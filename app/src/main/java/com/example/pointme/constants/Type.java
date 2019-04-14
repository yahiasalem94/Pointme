package com.example.pointme.constants;

import android.support.annotation.IntDef;

@IntDef({Type.EVENT, Type.APPOINTMENT})
public @interface Type {
    int EVENT = 1;
    int APPOINTMENT = 2;
}
