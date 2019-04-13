package com.example.pointme.constants;

import android.support.annotation.IntDef;

@IntDef({ApprovalStatus.DECLINED, ApprovalStatus.PENDING, ApprovalStatus.APPROVED})
public @interface ApprovalStatus {
    int DECLINED = -1;
    int PENDING = 0;
    int APPROVED = 1;
}
