package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.IOnBackPress;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CartAdapter;
import com.example.onlinemarket.model.Cart;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CartDBRepository;
import com.example.onlinemarket.repository.CustomerDBRepository;

import java.util.ArrayList;
import java.util.List;


public class ShoppingBagFragment extends Fragment implements IOnBackPress {

    public static final String TAG = "ShoppingFragment";
    private CustomerDBRepository mCustomerDBRepository;
    private CartDBRepository mCartDBRepository;
    private RecyclerView mShoppingRecyclerView;
    private Button mButtonFinalizeShopping;
    private CartAdapter mCartAdapter;


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
        mCustomerDBRepository = CustomerDBRepository.getInstance(getActivity());
        mCartDBRepository=CartDBRepository.getInstance(getActivity());
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
                if (mCustomerDBRepository.getCustomer() == null) {
                    replaceSignUpFragment();
                }else {
                    // TODO: going to the pay the orders
                }

            }
        });
        return view;
    }

    private void replaceSignUpFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main_activity,
                        SignUpFragment.newInstance())
                .commit();
    }


    private void initViews() {
        mShoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        List<Product> orderList = getCartProductList();

        updateUI(mShoppingRecyclerView,orderList);

    }

    private List<Product> getCartProductList() {
        List<Cart>  cartList= mCartDBRepository.getCarts();
        List<Product> orderList=new ArrayList<>();
        for (int i = 0; i <cartList.size() ; i++) {
            orderList.add(cartList.get(i).getProduct());
        }
        return orderList;
    }


    private void updateUI(RecyclerView recyclerView,

                          List<Product> productItems) {

        if (mCartAdapter == null) {
            mCartAdapter = new CartAdapter(getContext(), productItems);
            recyclerView.setAdapter(mCartAdapter);
        } else {
            mCartAdapter.setCartProducts(productItems);
            mCartAdapter.notifyDataSetChanged();
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