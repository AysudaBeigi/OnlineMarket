package com.example.onlinemarket.controller.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CommentAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.model.Card;
import com.example.onlinemarket.model.Comment;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CardDBRepository;
import com.example.onlinemarket.repository.CommentRepository;
import com.example.onlinemarket.repository.CustomerDBRepository;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;


public class ProductDetailFragment extends Fragment  {

    public static final String ARGS_PRODUCT = "argsProduct";
    private Product mProduct;
    private ImageSliderAdapter mImageSliderAdapter;
    private SliderView mSliderView;
    private MaterialTextView mOldPrice;
    private MaterialTextView mLatestPrice;
    private MaterialTextView mInformation;
    private MaterialTextView mName;
    private MaterialButton mButtonAddToShoppingBag;
    private CardDBRepository mCardDBRepository;
    private RecyclerView mRecyclerViewComments;
    private MaterialButton mButtonPostComment;
    private MaterialTextView mTextViewHaveNotComment;
    private CommentAdapter mCommentAdapter;
    private View mViewShowSnackBar;
    private Customer mCustomer;
    public static String TAG = "OnlineMarket";
   private NavController mNavController;



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

        View view = inflater.inflate(R.layout.fragment_product_detail,
                container, false);
        findViews(view);
        initViews();
        setListener();
        return view;
    }

    private void findViews(View view) {
        Log.d(TAG, "ProductDetailFragment +findViews ");

        mSliderView = view.findViewById(R.id.slider_view_product_detail);
        mOldPrice = view.findViewById(R.id.text_view_old_price_product_detail);
        mInformation = view.findViewById(R.id.text_view_information_product_detail);
        mLatestPrice = view.findViewById(R.id.text_view_latest_price_product_detail);
        mButtonAddToShoppingBag = view.findViewById(R.id.button_add_to_shopping_bag);
        mName = view.findViewById(R.id.text_view_name_prodcut_detail);
        mRecyclerViewComments = view.findViewById(R.id.recycler_view_comments);
        mButtonPostComment = view.findViewById(R.id.button_post_comment_product_detail);
        mTextViewHaveNotComment = view.findViewById(R.id.text_view_have_not_comment);
        mViewShowSnackBar = view.findViewById(R.id.layout_show_snack_bar_product_detail);

    }

    private void initViews() {
        Log.d(TAG, "ProductDetailFragment +initViews ");

        mLatestPrice.setText(mProduct.getPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mOldPrice.setText(mProduct.getRegularPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mOldPrice.setPaintFlags(mOldPrice.getPaintFlags() |
                Paint.STRIKE_THRU_TEXT_FLAG);
        mInformation.setText(getInformation());
        mName.setText(mProduct.getName());
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

                mTextViewHaveNotComment.setVisibility(View.GONE);
                mRecyclerViewComments.setVisibility(View.VISIBLE);
            }

        }
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(getContext(), comments);
            mRecyclerViewComments.setAdapter(mCommentAdapter);
        } else {
            mCommentAdapter.setComments(comments);
            mCommentAdapter.notifyDataSetChanged();

        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "ProductDetailFragment +initRecyclerView ");

        mRecyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext(),
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

        mButtonAddToShoppingBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ProductDetailFragment +mButtonAddToShoppingBag + onClick");

                if (!isProductInCart()&&mProduct.getPrice()!=null) {
                    Log.d(TAG, "ProductDetailFragment + isProductInCart"
                            +isProductInCart());

                    Log.d(TAG, "ProductDetailFragment +mButtonAddToShoppingBag + else");
                    Snackbar snackbar = UIUtils.makeSnackBar(mViewShowSnackBar
                            , R.string.this_product_is_added_to_shopping_bag);
                    snackbar.show();
                    addTooCard();
                    mButtonAddToShoppingBag.setEnabled(false);
                }
            }
        });

        mButtonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ProductDetailFragment +mButtonPostComment + onClick");
                if (mCustomer == null) {
                    Snackbar snackbar = UIUtils.makeSnackBar(mViewShowSnackBar,
                            R.string.please_fist_sign_up);
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

        /*getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_main_activity,
                        PostCommentFragment.newInstance(mProduct))
                .commit();
        */

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

        mSliderView.setSliderAdapter(mImageSliderAdapter);
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