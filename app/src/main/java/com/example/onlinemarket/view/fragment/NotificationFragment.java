package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.NotificationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
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
    private View mViewShowSnackBar;
    private NavController mNavController;


    public NotificationFragment() {
        // Required empty public constructor
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
        updateViews();
        setListeners(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController= Navigation.findNavController(view);
    }

    private void setListeners(LifecycleOwner owner) {

        mSwitchMaterialOnOffNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNotificationViewModel.togglePolling(owner);
                updateViews();
            }
        });

        mRadioGroupScheduleNotif.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == mRadioButton3Hours.getId()) {
                    setRadioButtonCheckedAndNotifTime(mRadioButton3Hours, 3);
                } else if (checkedId == mRadioButton5Hours.getId()) {
                    setRadioButtonCheckedAndNotifTime(mRadioButton5Hours, 5);
                } else if (checkedId == mRadioButton8Hours.getId()) {
                    setRadioButtonCheckedAndNotifTime(mRadioButton8Hours, 8);
                } else if (checkedId == mRadioButton12Hours.getId()) {
                    setRadioButtonCheckedAndNotifTime(mRadioButton12Hours, 12);
                } else {
                    mRadioButtonNonOfThem.setChecked(true);
                    mEditTextUserSchedule.setVisibility(View.VISIBLE);
                }
            }
        });
        mButtonDoSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioButtonNonOfThem.isChecked()) {

                    if (mEditTextUserSchedule.getText().toString().isEmpty()) {
                        Snackbar snackbar =
                                UIUtils.makeSnackBar(mViewShowSnackBar,
                                        R.string.first_enter_your_notif_time);
                        snackbar.show();
                    } else {
                        mNotificationViewModel.setNotificationTime(
                                Integer.parseInt(mEditTextUserSchedule.getText().toString()));
                        mNavController.navigate(R.id.action_NotificationFragment_to_UserProfileFragment);
                    }
                }else {
                    mNavController.navigate(R.id.action_NotificationFragment_to_UserProfileFragment);

                }
            }
        });

    }

    private void setRadioButtonCheckedAndNotifTime(MaterialRadioButton radioButton, int time) {
        radioButton.setChecked(true);
        mNotificationViewModel.setNotificationTime(time);
    }

    private void updateViews() {
        // if task scheduled then we have to set views
        setVisibility();
        int time = mNotificationViewModel.getNotificationTime();
        switch (time) {
            case 3:
                mRadioButton3Hours.setChecked(true);
                break;
            case 5:
                mRadioButton5Hours.setChecked(true);
                break;
            case 8:
                mRadioButton8Hours.setChecked(true);
                break;
            case 12:
                mRadioButton12Hours.setChecked(true);
                break;
            default:
                mRadioButtonNonOfThem.setChecked(true);
                mEditTextUserSchedule.setText(String.valueOf(time));
        }


    }

    private void setVisibility() {
        if (mNotificationViewModel.isTaskScheduled()) {
            mSwitchMaterialOnOffNotif.setChecked(true);
            mRadioGroupScheduleNotif.setVisibility(View.VISIBLE);
            mButtonDoSchedule.setVisibility(View.VISIBLE);
        } else {
            mSwitchMaterialOnOffNotif.setChecked(false);
            mRadioGroupScheduleNotif.setVisibility(View.GONE);
            mButtonDoSchedule.setVisibility(View.GONE);
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
        mViewShowSnackBar = view.findViewById(R.id.layout_show_snack_bar_user_time_cant_empty);
    }
}