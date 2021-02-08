package com.example.onlinemarket.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ImageSliderAdapter;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.MarketRepository;
import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;


public class ProductDetailFragment extends Fragment implements IOnBackPress {

    private static final String ARGS_PRODUCT ="argsProduct" ;
    //private ShoppingRepository mShoppingRepository;

    private MarketRepository mMarketRepository;
    private Product mProduct;

    private ImageSliderAdapter mImageSliderAdapter;
    private SliderView mSliderView;

    private TextView mRegularPrice;
    private TextView mFinalePrice;
    private TextView mDescription;
    private TextView mName;
    private Button mAddToBag;

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

        mMarketRepository = new MarketRepository(getContext());
        mProduct = (Product) getArguments().get(ARGS_PRODUCT);
       // mShoppingRepository = ShoppingRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        mAddToBag = view.findViewById(R.id.add_to_shop);
        mName = view.findViewById(R.id.product_detail_name);
    }

    private void initViews() {
        mFinalePrice.setText(mProduct.getPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mRegularPrice.setText(mProduct.getRegularPrice() + " " +
                getContext().getResources().getString(R.string.toman));
        mRegularPrice.setPaintFlags(mRegularPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mDescription.setText(getDescription());
        mName.setText(mProduct.getName());
        setupSliderAdapter(mProduct.getImages());

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
        return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
    }

    private void setListener() {
        mAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupSliderAdapter(List<Image> imagesItems) {
        mImageSliderAdapter = new ImageSliderAdapter(getContext(), imagesItems);
        mSliderView.setSliderAdapter(mImageSliderAdapter);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

}