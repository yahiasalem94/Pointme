package com.example.pointme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pointme.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    public static final String TAG = ActionBottomDialogFragment.class.getSimpleName();
    private BottomSheetListener mListener;
    public static ActionBottomDialogFragment newInstance() {
        return new ActionBottomDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (BottomSheetListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement BottomSheetListener interface");
        }
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_choose_image_dialog, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.gallery).setOnClickListener(this);
        view.findViewById(R.id.camera).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof BottomSheetListener) {
//            mListener = (BottomSheetListener) getParentFragment();
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement BottomSheetListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onClick(View view) {
        mListener.onBottomSheetItemClick(view.getId());
        dismiss();
    }
    public interface BottomSheetListener {
        void onBottomSheetItemClick(int id);
    }
}