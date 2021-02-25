package com.example.onlinemarket.view.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CommentAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.databinding.FragmentProductDetailBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.ProductDetailViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class ProductDetailFragment extends Fragment {

    public static final String ARGS_PRODUCT = "argsProduct";
    private ImageSliderAdapter mImageSliderAdapter;
    private CommentAdapter mCommentAdapter;
    public static String TAG = "OnlineMarket";
    private NavController mNavController;
    private FragmentProductDetailBinding mBinding;
    private ProductDetailViewModel mProductDetailViewModel;

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ProductDetailFragment +onCreate ");
        initData();
    }

    private void initData() {
        mProductDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        mProductDetailViewModel.setProductComments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ProductDetailFragment +onCreateView ");

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_product_detail,
                container, false);
        initViews();
        setObservers();
        setListener();
        return mBinding.getRoot();
    }

    private void initViews() {
        Log.d(TAG, "ProductDetailFragment +initViews ");

        mBinding.textViewOldPriceProductDetail.
                setPaintFlags(mBinding.textViewOldPriceProductDetail.getPaintFlags() |
                        Paint.STRIKE_THRU_TEXT_FLAG);

        mBinding.setProductDetailViewModel(mProductDetailViewModel);

       /* mBinding.textViewLatestPriceProductDetail.setText(mProductDetailViewModel.getPrice());
        mBinding.textViewOldPriceProductDetail.setText(mProductDetailViewModel.getRegularPrice());
        mBinding.textViewInformationProductDetail.setText(mProductDetailViewModel.getInformation());
        mBinding.textViewNameProdcutDetail.setText(mProductDetailViewModel.getName());
      */
        setupImageSliderAdapter(mProductDetailViewModel.getImages());

    }

    private void setObservers() {
        mProductDetailViewModel.getProductCommentsLiveData().
                observe(this, new Observer<List<Comment>>() {
                    @Override
                    public void onChanged(List<Comment> comments) {
                        Log.d(TAG, "ProductDetailFragment +onItemResponse ");
                        initRecyclerView();
                        setupCommentAdapter(comments);

                    }
                });
    }

    private void setupCommentAdapter(List<Comment> comments) {
        Log.d(TAG, "ProductDetailFragment +initCommentAdapter ");

       // if (comments != null) {
           // if (comments.size() > 0) {

                mBinding.setHaveAnyComment(comments.size() > 0);
               /* mBinding.textViewHaveNotComment.setVisibility(View.GONE);
                mBinding.recyclerViewComments.setVisibility(View.VISIBLE);
           */ //}

      //  }
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(getContext(), comments,this);
            mBinding.recyclerViewComments.setAdapter(mCommentAdapter);
        } else {
            mCommentAdapter.setComments(comments);
            mCommentAdapter.notifyDataSetChanged();

        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "ProductDetailFragment +initRecyclerView ");

        mBinding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }


    private void setListener() {
        Log.d(TAG, "ProductDetailFragment +setListener ");

        mBinding.buttonAddToShoppingBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ProductDetailFragment +mButtonAddToShoppingBag + onClick");

                if (mProductDetailViewModel.isNotProductInCardAndPriceNotNull()) {
                    Snackbar snackbar = UIUtils.makeSnackBar(mBinding.layoutShowSnackBarProductDetail
                            , R.string.this_product_is_added_to_shopping_bag);
                    snackbar.show();
                    mProductDetailViewModel.addTooCard();
                    mBinding.buttonAddToShoppingBag.setEnabled(false);

                }
            }
        });

        mBinding.buttonPostCommentProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ProductDetailFragment +mButtonPostComment + onClick");
                if ( mProductDetailViewModel.getCustomer()== null) {
                    Snackbar snackbar = UIUtils.makeSnackBar(mBinding.layoutShowSnackBarProductDetail
                            , R.string.please_fist_sign_up);
                    snackbar.show();
                } else {
                    replacePostCommentFragment();
                }

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    private void replacePostCommentFragment() {

        mNavController.navigate(R.id.action_productDetailFragment_to_PostCommentFragment);

    }

    private void setupImageSliderAdapter(List<Image> images) {
        Log.d(TAG, "ProductDetailFragment +setupImageSliderAdapter ");
        Log.d(TAG, "ProductDetailFragment +setupImageSliderAdapter+ first image is  "
                + images.get(0).getSrc());

        mImageSliderAdapter = new ImageSliderAdapter(getContext(), images);
        Log.d(TAG, "ProductDetailFragment + after setupImageSliderAdapter ");

        mBinding.sliderViewProductDetail.setSliderAdapter(mImageSliderAdapter);
    }

   /* public String getFormattedDescription() {
        if (mProduct.getName() == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(mProduct.getDescription(),
                    Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(mProduct.getDescription()).toString();
        }
    }*/


}