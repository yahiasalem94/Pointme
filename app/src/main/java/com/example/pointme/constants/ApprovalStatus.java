package com.example.pointme.constants;

import androidx.annotation.IntDef;

@IntDef({ApprovalStatus.DECLINED, ApprovalStatus.PENDING, ApprovalStatus.APPROVED})
public @interface ApprovalStatus {
    int DECLINED = -1;
    int PENDING = 0;
    int APPROVED = 1;
}
