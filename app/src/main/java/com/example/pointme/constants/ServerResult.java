package com.example.pointme.constants;

import android.support.annotation.IntDef;

@IntDef({ServerResult.SUCCESS, ServerResult.FAILURE})
public @interface ServerResult {
    int SUCCESS = 1;
    int FAILURE = 0;
}
