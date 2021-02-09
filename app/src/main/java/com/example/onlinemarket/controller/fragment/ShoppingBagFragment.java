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
                    //todo : snack bar please sign up first

                    /*else {
                        int count = cart.getProduct_count() + 1;
                        cart.setProduct_count(count);
                        mCartDBRepository.updateCart(cart);
                    }*/
                }

            }
        });
        return view;
    }

   /* private void setTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < mProductList.size(); i++) {
            int price = Integer.parseInt(mProductList.get(i).getPrice());
            int count = mCartViewModel.getCart(mProductList.get(i).getId()).getProduct_count();
            totalPrice += (price * count);
        }
        mBuyBinding.totalPrice.setText(String.valueOf(totalPrice));
    }*/
    private void initViews() {
        mShoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        List<Product> orderList = getOrderList();

        updateUI(mShoppingRecyclerView,orderList);

    }

    private List<Product> getOrderList() {
        List<Cart>  cartList= mCartDBRepository.getCarts();
        List<Product> orderList=new ArrayList<>();
        for (int i = 0; i <cartList.size() ; i++) {
            orderList.add(cartList.get(i).getProduct());
        }
        return orderList;
    }

   /* public void onClickToBuy(int productId) {
        Cart cart = mCartDBRepository.getCart(productId);
        if (cart == null) {
            insertToCart(new Cart(productId,1));
        }else {
            int count = cart.getProduct_count() + 1;
            cart.setProduct_count(count);
            mCartDBRepository.updateCart(cart);
        }
        Toast.makeText(mContext, "add to cart", Toast.LENGTH_SHORT).show();

    }*/
/*
    public void onClickToBuyAgain(int productId) {
        onClickToBuy(productId);
        mOrderedProductAdapter.notifyDataSetChanged();
        mFragmentCartBinding.totalPrice.setText(String.valueOf(getTotalPrice()));
    }

    public void onClickToDelete(int productId) {
        if (mCartDBRepository.getCart(productId).getProduct_count() == 1) {
            mCartDBRepository.deleteCart(mCartDBRepository.getCart(productId));
            for (int i = 0; i < mProductList.size(); i++) {
                if (mProductList.get(i).getId() == productId)
                    mProductList.remove(i);
            }
        }
        else {
            Cart updateCart = mCartDBRepository.getCart(productId);
            int count = updateCart.getProduct_count() - 1;
            updateCart.setProduct_count(count);
            mCartDBRepository.updateCart(updateCart);

        }
        if (mProductList.size() == 0){
            mFragmentCartBinding.recyclerCart.setVisibility(View.GONE);
            mFragmentCartBinding.layoutEmptyCart.setVisibility(View.VISIBLE);
        }
        mFragmentCartBinding.totalPrice.setText(String.valueOf(getTotalPrice()));
        mOrderedProductAdapter.notifyDataSetChanged();
    }

    public void setOrderedProductAdapter(OrderedProductAdapter orderedProductAdapter) {
        mOrderedProductAdapter = orderedProductAdapter;
    }*/


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