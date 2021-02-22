package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CommentRepository;
import com.example.onlinemarket.data.repository.CustomerDBRepository;
import com.example.onlinemarket.databinding.FragmentPostCommentBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.snackbar.Snackbar;

public class PostCommentFragment extends Fragment {
    public static final String ARGS_PRODUCT = "argsProduct";
    private boolean mIsRated = false;
    private int mRate;
    private int mProductId;
    private Product mProduct;
    private Customer mCustomer;
    private NavController mNavController;
    public static String TAG = "OnlineMarket";
    private FragmentPostCommentBinding mBinding;
    private CommentRepository mCommentRepository;

    public PostCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "PostCommentFragment +onCreate");

        mProduct = (Product) getArguments().getSerializable(ARGS_PRODUCT);
        mProductId = mProduct.getId();
        mCustomer = CustomerDBRepository.getInstance(getActivity())
                .getCustomer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_post_comment, container,
                false);

        setListeners(mBinding.getRoot());
        return mBinding.getRoot();
    }

    private void setListeners(View view) {
        mBinding.buttonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = mBinding.editTextComment.getText().toString();

                if (review.isEmpty() || !mIsRated) {
                    showCompleteCommentSnackBar(view);

                } else {
                    Log.d(TAG, "PostCommentFragment + comment is complete");

                    mBinding.buttonPostComment.setBackgroundColor(
                            getResources().getColor(R.color.digikala_red));

                    Comment comment = getComment(review);

                    CommentRepository.getInstance(getActivity())
                            .postComment(comment, new CommentRepository.CommentCallback() {
                                @Override
                                public void onItemResponse(Comment comment) {
                                    Log.d(TAG, "PostCommentFragment + postComment+" +
                                            " onItemResponse");
                                    replaceProductDetailFragment();
                                }
                            });

                }
            }

        });
        mBinding.radioGroupRatingAddComment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mBinding.radioButton1AddComment.getId()) {
                    rating(mBinding.radioButton1AddComment, 1);
                } else if (checkedId == mBinding.radioButton2AddComment.getId()) {
                    rating(mBinding.radioButton2AddComment, 2);
                } else if (checkedId == mBinding.radioButton3AddComment.getId()) {
                    rating(mBinding.radioButton3AddComment, 3);
                } else if (checkedId == mBinding.radioButton4AddComment.getId()) {
                    rating(mBinding.radioButton4AddComment, 4);
                } else if (checkedId == mBinding.radioButton5AddComment.getId()) {
                    rating(mBinding.radioButton5AddComment, 5);
                } else {
                    mIsRated = false;
                }

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);

    }

    private void replaceProductDetailFragment() {
        Log.d(TAG, "PostCommentFragment +replaceProductDetailFragment");

        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductDetailFragment.ARGS_PRODUCT, mProduct);
        mNavController.navigate(R.id.action_PostCommentFragment_to_productDetailFragment
                , bundle);


    }

    private Comment getComment(String review) {
        Log.d(TAG, "PostCommentFragment +getComment");

        return new Comment(mProductId, review,
                mCustomer.getEmail(), mRate);
    }

    private void showCompleteCommentSnackBar(View view) {
        Log.d(TAG, "PostCommentFragment +showCompleteCommentSnackBar");

        Snackbar snackbar = UIUtils.makeSnackBar(
                view.findViewById(R.id.layout_show_snack_bar_post_comment),
                R.string.please_complete_comment);
        snackbar.show();
    }


    private void rating(RadioButton radioButton, int rate) {
        radioButton.setChecked(true);
        mRate = rate;
        mIsRated = true;
    }


}