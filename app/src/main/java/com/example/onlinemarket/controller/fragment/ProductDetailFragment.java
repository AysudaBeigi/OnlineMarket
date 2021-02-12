package com.example.onlinemarket.controller.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
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
import com.example.onlinemarket.model.Card;
import com.example.onlinemarket.model.Comment;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CardDBRepository;
import com.example.onlinemarket.repository.CommentRepository;
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
    private CardDBRepository mCardDBRepository;
    private RecyclerView mRecyclerViewComments;
    private MaterialButton mButtonPostComment;
    private MaterialTextView mTextViewHaveNotComment;
    private CommentAdapter mCommentAdapter;
    public static String TAG = "OnlineMarket";


    public ProductDetailFragment() {
        // Required empty public constructor
    }


    public static ProductDetailFragment newInstance(Product product) {
        Log.d(TAG,"ProductDetailFragment +newInstance ");
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"ProductDetailFragment +onCreate ");

        mProduct = (Product) getArguments().get(ARGS_PRODUCT);
        mCardDBRepository = CardDBRepository.getInstance(getActivity());
        Log.d(TAG,"ProductDetailFragment +product name is  "+mProduct.getName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"ProductDetailFragment +onCreateView ");

        View view = inflater.inflate(R.layout.fragment_product_detail,
                container, false);
        findViews(view);
        initViews();
        setListener();
        return view;
    }

    private void findViews(View view) {
        Log.d(TAG,"ProductDetailFragment +findViews ");

        mSliderView = view.findViewById(R.id.fragment_home_slider);
        mRegularPrice = view.findViewById(R.id.old_price);
        mDescription = view.findViewById(R.id.product_detail_description);
        mFinalePrice = view.findViewById(R.id.latest_price);
        mButtonAddToShoppingBag = view.findViewById(R.id.button_add_to_card);
        mName = view.findViewById(R.id.product_detail_name);
        mRecyclerViewComments = view.findViewById(R.id.recycler_view_comments);
        mButtonPostComment = view.findViewById(R.id.button_post_comment_product_detail);
        mTextViewHaveNotComment = view.findViewById(R.id.text_view_have_not_comment);

    }

    private void initViews() {
        Log.d(TAG,"ProductDetailFragment +initViews ");

        mFinalePrice.setText(mProduct.getPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mRegularPrice.setText(mProduct.getRegularPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mRegularPrice.setPaintFlags(mRegularPrice.getPaintFlags() |
                Paint.STRIKE_THRU_TEXT_FLAG);
        mDescription.setText(getDescription());
        mName.setText(mProduct.getName());
        setupImageSliderAdapter(mProduct.getImages());
        CommentRepository.getInstance(getActivity()).
                fetchProductComments(mProduct.getId(),
                        new CommentRepository.CommentsCallback() {
                    @Override
                    public void onItemResponse(List<Comment> comments) {

                        Log.d(TAG,"ProductDetailFragment +onItemResponse ");
                        initRecyclerView();
                        initCommentAdapter(comments);
                    }
                });

    }

    private void initCommentAdapter(List<Comment> comments) {
        Log.d(TAG,"ProductDetailFragment +initCommentAdapter ");

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
        Log.d(TAG,"ProductDetailFragment +initRecyclerView ");

        mRecyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

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
        Log.d(TAG,"ProductDetailFragment +setListener ");

        mButtonAddToShoppingBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"ProductDetailFragment +mButtonAddToShoppingBag + onClick");

                if (!isProductInCart()) {
                    addTooCard();

                } else {
                    Log.d(TAG,"ProductDetailFragment +mButtonAddToShoppingBag + else");

                    //todo: show snack bar : this is is added
                }
            }
        });

        mButtonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"ProductDetailFragment +mButtonPostComment + onClick");


                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                PostCommentFragment.newInstance(mProduct))
                        .commit();
            }
        });

    }


    public boolean isProductInCart() {
        Log.d(TAG,"ProductDetailFragment +isProductInCart ");

        Card card = new Card(mProduct, mProduct.getId(), 1);
        return mCardDBRepository.getCarts().contains(card);
    }

    public void addTooCard() {
        Log.d(TAG,"ProductDetailFragment +addTooCard ");

        Card card = new Card(mProduct, mProduct.getId(), 1);

        mCardDBRepository.insertCart(card);


    }

    private void setupImageSliderAdapter(List<Image> imagesItems) {
        Log.d(TAG,"ProductDetailFragment +setupImageSliderAdapter ");
        Log.d(TAG,"ProductDetailFragment +setupImageSliderAdapter+ first image is  "
        +imagesItems.get(0).getSrc());

        mImageSliderAdapter = new ImageSliderAdapter(getContext(), imagesItems);
        Log.d(TAG,"ProductDetailFragment + after setupImageSliderAdapter ");

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