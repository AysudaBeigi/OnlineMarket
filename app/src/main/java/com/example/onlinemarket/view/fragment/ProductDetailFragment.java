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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CommentAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CardDBRepository;
import com.example.onlinemarket.data.repository.CommentRepository;
import com.example.onlinemarket.data.repository.CustomerDBRepository;
import com.example.onlinemarket.databinding.FragmentProductDetailBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;


public class ProductDetailFragment extends Fragment  {

    public static final String ARGS_PRODUCT = "argsProduct";
    private Product mProduct;
    private ImageSliderAdapter mImageSliderAdapter;
   private CardDBRepository mCardDBRepository;
    private CommentAdapter mCommentAdapter;
    private Customer mCustomer;
    public static String TAG = "OnlineMarket";
   private NavController mNavController;
   private FragmentProductDetailBinding mBinding;

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    public static ProductDetailFragment newInstance() {
        Log.d(TAG, "ProductDetailFragment +newInstance ");
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ProductDetailFragment +onCreate ");

        mProduct = (Product) getArguments().get(ARGS_PRODUCT);
        mCardDBRepository = CardDBRepository.getInstance(getActivity());
        mCustomer = CustomerDBRepository.getInstance(getActivity())
                .getCustomer();
        Log.d(TAG, "ProductDetailFragment +product name is  " + mProduct.getName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ProductDetailFragment +onCreateView ");

       mBinding = DataBindingUtil.inflate(inflater,
               R.layout.fragment_product_detail,
                container, false);
        initViews();
        setListener();
        return mBinding.getRoot();
    }

    private void initViews() {
        Log.d(TAG, "ProductDetailFragment +initViews ");

        mBinding.textViewLatestPriceProductDetail.setText(mProduct.getPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mBinding.textViewOldPriceProductDetail.setText(mProduct.getRegularPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mBinding.textViewOldPriceProductDetail.
                setPaintFlags(mBinding.textViewOldPriceProductDetail.getPaintFlags() |
                Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.textViewInformationProductDetail.setText(getInformation());
        mBinding.textViewNameProdcutDetail.setText(mProduct.getName());
        setupImageSliderAdapter(mProduct.getImages());
        CommentRepository.getInstance(getActivity()).
                fetchProductComments(mProduct.getId(),
                        new CommentRepository.CommentsCallback() {
                            @Override
                            public void onItemResponse(List<Comment> comments) {

                                Log.d(TAG, "ProductDetailFragment +onItemResponse ");
                                initRecyclerView();
                                initCommentAdapter(comments);
                            }
                        });

    }

    private void initCommentAdapter(List<Comment> comments) {
        Log.d(TAG, "ProductDetailFragment +initCommentAdapter ");

        if (comments != null) {
            if (comments.size() > 0) {

                mBinding.textViewHaveNotComment.setVisibility(View.GONE);
                mBinding.recyclerViewComments.setVisibility(View.VISIBLE);
            }

        }
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(getContext(), comments);
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

    private String getInformation() {
        String description = mProduct.getDescription();
        if (description.equals(null))
            return description;
        Document document = Jsoup.parse(description);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        return Jsoup.clean(s, "", Whitelist.none(),
                new Document.OutputSettings().prettyPrint(false));


    }

    private void setListener() {
        Log.d(TAG, "ProductDetailFragment +setListener ");

        mBinding.buttonAddToShoppingBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ProductDetailFragment +mButtonAddToShoppingBag + onClick");

                if (!isProductInCart()&&mProduct.getPrice()!=null) {
                    Log.d(TAG, "ProductDetailFragment + isProductInCart"
                            +isProductInCart());

                    Log.d(TAG, "ProductDetailFragment +mButtonAddToShoppingBag + else");
                    Snackbar snackbar = UIUtils.makeSnackBar(mBinding.layoutShowSnackBarProductDetail
                            , R.string.this_product_is_added_to_shopping_bag);
                    snackbar.show();
                    addTooCard();
                    mBinding.buttonAddToShoppingBag.setEnabled(false);
                }
            }
        });

        mBinding.buttonPostCommentProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ProductDetailFragment +mButtonPostComment + onClick");
                if (mCustomer == null) {
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
        mNavController=Navigation.findNavController(view);
    }

    private void replacePostCommentFragment() {

        Bundle bundle=new Bundle();
        bundle.putSerializable(ProductDetailFragment.ARGS_PRODUCT,mProduct);
        mNavController.navigate(R.id.action_productDetailFragment_to_PostCommentFragment,bundle);


    }


    public boolean isProductInCart() {
        Log.d(TAG, "ProductDetailFragment +isProductInCart ");

        Card card = new Card(mProduct, mProduct.getId(), 1);
        return mCardDBRepository.getCarts().contains(card);
    }

    public void addTooCard() {
        Log.d(TAG, "ProductDetailFragment +addTooCard ");

        Card card = new Card(mProduct, mProduct.getId(), 1);

        mCardDBRepository.insertCart(card);


    }

    private void setupImageSliderAdapter(List<Image> imagesItems) {
        Log.d(TAG, "ProductDetailFragment +setupImageSliderAdapter ");
        Log.d(TAG, "ProductDetailFragment +setupImageSliderAdapter+ first image is  "
                + imagesItems.get(0).getSrc());

        mImageSliderAdapter = new ImageSliderAdapter(getContext(), imagesItems);
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