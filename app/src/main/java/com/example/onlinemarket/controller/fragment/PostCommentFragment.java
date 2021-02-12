package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Comment;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CommentRepository;
import com.example.onlinemarket.repository.CustomerDBRepository;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class PostCommentFragment extends Fragment {
    public static final String ARGS_PRODUCT = "argsProduct";
    private TextInputEditText mEditTextComment;
    private RadioGroup mRadioGroupRating;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private RadioButton mRadioButton5;
    private MaterialButton mButtonPostComment;
    private boolean mIsRated = false;
    private int mRate;
    private int mProductId;
    private Product mProduct;
    private Customer mCustomer;

    public PostCommentFragment() {
        // Required empty public constructor
    }

    public static PostCommentFragment newInstance(Product product) {
        PostCommentFragment fragment = new PostCommentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = (Product) getArguments().getSerializable(ARGS_PRODUCT);
        mProductId = mProduct.getId();
        mCustomer = CustomerDBRepository.getInstance(getActivity())
                .getCustomer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_comment, container,
                false);
        findViews(view);
        setListeners(view);
        return view;
    }

    private void setListeners(View view) {
        mButtonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = mEditTextComment.getText().toString();
                if (mCustomer == null) {
                    replaceSignUpFragment();
                } else {
                    if (review.isEmpty() || !mIsRated) {
                        showCompleteCommentSnackBar(view);

                    } else {
                        mButtonPostComment.setBackgroundColor(
                                getResources().getColor(R.color.digikala_red));

                        Comment comment = getComment(review);

                        CommentRepository.getInstance(getActivity())
                                .postComment(comment, new CommentRepository.CommentCallback() {
                                    @Override
                                    public void onItemResponse(Comment comment) {
                                        replaceProductDetailFragment();
                                    }
                                });
                    }
                }
            }
        });
        mRadioGroupRating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mRadioButton1.getId()) {
                    rating(mRadioButton1, 1);
                } else if (checkedId == mRadioButton2.getId()) {
                    rating(mRadioButton2, 2);
                } else if (checkedId == mRadioButton3.getId()) {
                    rating(mRadioButton3, 3);
                } else if (checkedId == mRadioButton4.getId()) {
                    rating(mRadioButton4, 4);
                } else if (checkedId == mRadioButton5.getId()) {
                    rating(mRadioButton5, 5);
                } else {
                    mIsRated = false;
                }

            }
        });
    }

    private void replaceProductDetailFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_main_activity
                        , ProductDetailFragment.newInstance(mProduct))
                .commit();
    }

    private Comment getComment(String review) {
        return new Comment(mProductId, review,
                                    mCustomer.getEmail(), mRate);
    }

    private void showCompleteCommentSnackBar(View view) {
        Snackbar snackbar = UIUtils.makeSnackBar(
                view.findViewById(R.id.layout_show_snack_bar_post_comment),
                R.string.please_complete_comment);
        snackbar.show();
    }

    private void replaceSignUpFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main_activity,
                        SignUpFragment.newInstance());
    }

    private void rating(RadioButton radioButton, int rate) {
        radioButton.setChecked(true);
        mRate = rate;
        mIsRated = true;
    }

    private void findViews(View view) {
        mEditTextComment = view.findViewById(R.id.edit_text_comment);
        mRadioGroupRating = view.findViewById(R.id.radio_group_rating_add_comment);
        mRadioButton1 = view.findViewById(R.id.radio_button_1_add_comment);
        mRadioButton2 = view.findViewById(R.id.radio_button_2_add_comment);
        mRadioButton3 = view.findViewById(R.id.radio_button_3_add_comment);
        mRadioButton4 = view.findViewById(R.id.radio_button_4_add_comment);
        mRadioButton5 = view.findViewById(R.id.radio_button_5_add_comment);
        mButtonPostComment = view.findViewById(R.id.button_post_comment_fragment);
    }


}