package com.example.onlinemarket.controller.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CommentAdapter;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.adapter.ProductsHorizontalAdapter;
import com.example.onlinemarket.model.Card;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CartDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;


public class ProductDetailFragment extends Fragment implements IOnBackPress {

    private static final String ARGS_PRODUCT = "argsProduct";
    private Product mProduct;
    private ImageSliderAdapter mImageSliderAdapter;
    private SliderView mSliderView;
    private TextView mRegularPrice;
    private TextView mFinalePrice;
    private TextView mDescription;
    private TextView mName;
    private Button mButtonAddToShoppingBag;
    private CartDBRepository mCartDBRepository;
    private RecyclerView mRecyclerViewComments;
    private MaterialButton mButtonAddComment;
    private MaterialTextView mTextViewHaveNotComment;
    private CommentAdapter mCommentAdapter;
    public ProductDetailFragment() {
        // Required empty public constructor
    }


    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = (Product) getArguments().get(ARGS_PRODUCT);
        mCartDBRepository=CartDBRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        findViews(view);
        initViews();
        setListener();
        return view;
    }

    private void findViews(View view) {
        mSliderView = view.findViewById(R.id.fragment_home_slider);
        mRegularPrice = view.findViewById(R.id.old_price);
        mDescription = view.findViewById(R.id.product_detail_description);
        mFinalePrice = view.findViewById(R.id.latest_price);
        mButtonAddToShoppingBag = view.findViewById(R.id.add_to_shop);
        mName = view.findViewById(R.id.product_detail_name);
        mRecyclerViewComments=view.findViewById(R.id.recycler_view_comments);
        mButtonAddComment=view.findViewById(R.id.button_add_comment);
        mTextViewHaveNotComment=view.findViewById(R.id.text_view_have_not_comment);

    }

    private void initViews() {
        mFinalePrice.setText(mProduct.getPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mRegularPrice.setText(mProduct.getRegularPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mRegularPrice.setPaintFlags(mRegularPrice.getPaintFlags() |
                Paint.STRIKE_THRU_TEXT_FLAG);
        mDescription.setText(getDescription());
        mName.setText(mProduct.getName());
        setupImageSliderAdapter(mProduct.getImages());
        mRecyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        if (mCommentAdapter == null) {
            mCommentAdapter = new ProductsHorizontalAdapter(getContext());
            mRecyclerViewComments.setAdapter(mCommentAdapter);
        } else {
            mCommentAdapter.setComments(commments);
            mCommentAdapter.notifyDataSetChanged();

        }  }

    private String getDescription() {
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
        mButtonAddToShoppingBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isProductInCart()) {
                    addTooCart();

                }else {
                    //todo: show snack bar : this is is added
                }
            }
        });

        mButtonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
            .beginTransaction().
                        replace(R.id.fragment_container_main_activity,
                                AddCommentFragment.newInstance())
            .commit();
            }
        });
               /* mBinding.commentsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ReviewsFragment.ARG_PRODUCT_ID,
                                getArguments().getInt(ARG_PRODUCT_ID));
                        mNavController.navigate(R.id.action_productDetailsFragment_to_reviewsFragment, bundle);
                    }
                });*/

    }


    public boolean isProductInCart() {
        Card card = new Card(mProduct,mProduct.getId(), 1);
        return mCartDBRepository.getCarts().contains(card);
    }

    public void addTooCart() {
        Card card = new Card(mProduct,mProduct.getId(), 1);

        mCartDBRepository.insertCart(card);


    }
    private void setupImageSliderAdapter(List<Image> imagesItems) {
        mImageSliderAdapter = new ImageSliderAdapter(getContext(), imagesItems);
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

    @Override
    public boolean onBackPressed() {
        return true;
    }

}