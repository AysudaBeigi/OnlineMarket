package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.viewModel.NotificationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class NotificationFragment extends VisibleFragment {


    private RadioGroup mRadioGroupScheduleNotif;
    private MaterialRadioButton mRadioButton3Hours;
    private MaterialRadioButton mRadioButton5Hours;
    private MaterialRadioButton mRadioButton8Hours;
    private MaterialRadioButton mRadioButton12Hours;
    private MaterialRadioButton mRadioButtonNonOfThem;
    private TextInputEditText mEditTextUserSchedule;
    private MaterialButton mButtonDoSchedule;
    private SwitchMaterial mSwitchMaterialOnOffNotif;
    private NotificationViewModel mNotificationViewModel;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationViewModel = new ViewModelProvider(this)
                .get(NotificationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification,
                container, false);

        findViews(view);
        initViews();
        mSwitchMaterialOnOffNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void initViews() {
        if (mNotificationViewModel.isTaskScheduled()) {
            mSwitchMaterialOnOffNotif.setChecked(true);
            mRadioGroupScheduleNotif.setVisibility(View.VISIBLE);
            mEditTextUserSchedule.setVisibility(View.VISIBLE);
        } else {
            mSwitchMaterialOnOffNotif.setChecked(false);
            mRadioGroupScheduleNotif.setVisibility(View.GONE);
            mEditTextUserSchedule.setVisibility(View.GONE);
        }

    }

    private void findViews(View view) {
        mRadioGroupScheduleNotif = view.findViewById(R.id.radio_group_schedule_notif);
        mRadioButton3Hours = view.findViewById(R.id.radio_button_3_hours);
        mRadioButton5Hours = view.findViewById(R.id.radio_button_5_hours);
        mRadioButton8Hours = view.findViewById(R.id.radio_button_8_hours);
        mRadioButton12Hours = view.findViewById(R.id.radio_button_12_hours);
        mRadioButtonNonOfThem = view.findViewById(R.id.radio_button_non_of_them);
        mEditTextUserSchedule = view.findViewById(R.id.edit_text_enter_your_schedule);
        mButtonDoSchedule = view.findViewById(R.id.button_do_schedule);
        mSwitchMaterialOnOffNotif = view.findViewById(R.id.switch_on_off_notif);
    }
}