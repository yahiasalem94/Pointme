package com.example.pointme.constants;

import androidx.annotation.IntDef;

@IntDef({ServerResult.SUCCESS, ServerResult.FAILURE})
public @interface ServerResult {
    int SUCCESS = 1;
    int FAILURE = 0;
}
