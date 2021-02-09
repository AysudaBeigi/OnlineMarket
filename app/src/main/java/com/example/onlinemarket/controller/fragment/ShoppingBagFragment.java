package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ShoppingAdapter;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.MarketRepository;
import com.example.onlinemarket.repository.ShoppingBagRepository;

import java.util.List;


public class ShoppingBagFragment extends Fragment implements IOnBackPress {

    public static final String TAG = "ShoppingFragment";
    private MarketRepository mMarketRepository;
    private ShoppingBagRepository mShoppingBagRepository;

    private RecyclerView mShoppingRecyclerView;
    private Button mButtonFinalizeShopping;
    private ShoppingAdapter mShoppingAdapter;

    public ShoppingBagFragment() {
        // Required empty public constructor
    }

    public static ShoppingBagFragment newInstance() {
        ShoppingBagFragment fragment = new ShoppingBagFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMarketRepository = new MarketRepository(getContext());
        mShoppingBagRepository = ShoppingBagRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_bag, container,
                false);

        findViews(view);
        initViews();

        mButtonFinalizeShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Customer customer = new Customer();
                customer.setEmail("digikala.test@digikala.com");
                mMarketRepository.postCustomer(customer,
                        new MarketRepository.CustomerCallback() {
                            @Override
                            public void onItemResponse(Customer createCustomer) {
                                //TODO
                                //successfully create a customer
                            }
                        });
            }
        });
        return view;
    }


    private void initViews() {
        mShoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        updateList(mShoppingRecyclerView,
                mShoppingBagRepository.getProducts());

        Log.d(TAG, "setRecycler: " + mShoppingBagRepository.getProducts().size());
    }


    private void updateList(RecyclerView recyclerView,

                            List<Product> productItems) {

        if (mShoppingAdapter == null) {
            mShoppingAdapter = new ShoppingAdapter(getContext(), productItems);
            recyclerView.setAdapter(mShoppingAdapter);
        } else {
            mShoppingAdapter.setProductsItem(productItems);
            mShoppingAdapter.notifyDataSetChanged();
        }

    }

    private void findViews(View view) {
        mShoppingRecyclerView = view.findViewById(R.id.card_recycler_view);
        mButtonFinalizeShopping = view.findViewById(R.id.final_shop);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}