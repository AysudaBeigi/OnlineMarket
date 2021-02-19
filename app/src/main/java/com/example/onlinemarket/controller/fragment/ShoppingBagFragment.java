package com.example.onlinemarket.controller.fragment;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.CardAdapter;
import com.example.onlinemarket.databinding.FragmentShoppingBagBinding;
import com.example.onlinemarket.model.Card;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.order.LineItemsItem;
import com.example.onlinemarket.model.order.Order;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CardDBRepository;
import com.example.onlinemarket.repository.CustomerDBRepository;
import com.example.onlinemarket.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;


public class ShoppingBagFragment extends Fragment   {

    public static String TAG = "OnlineMarket";
    private CustomerDBRepository mCustomerDBRepository;
    private CardDBRepository mCardDBRepository;
    private CardAdapter mCardAdapter;
    private Customer mCustomer;
    private FragmentShoppingBagBinding mBinding;

    private NavController mNavController;


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
        mCardDBRepository = CardDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater,
                R.layout.fragment_shopping_bag, container,
                false);

        initViews();

        setListeners();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.buttonFinalizeShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomer = mCustomerDBRepository.getCustomer();
                if (mCustomer == null) {
                    replaceSignUpFragment();
                } else {

                    Order order = getOrder();
                    OrderRepository.getInstance(getActivity()).postOrder(order, new OrderRepository.OrderCallback() {
                        @Override
                        public void onItemResponse(Order order) {
                            Log.d(TAG, "postOrder+ onItemResponse + order is " +
                                    order.toString());

                        }
                    });
                    //todo : go to pay for product
                }

            }
        });
    }

    private Order getOrder() {
        List<LineItemsItem> lineItemsItemList = getLineItemsItemList();
        Order order = new Order();
        order.setCustomerId(mCustomer.getId());
        order.setLineItems(lineItemsItemList);
        return order;
    }

    private List<LineItemsItem> getLineItemsItemList() {
        List<LineItemsItem> lineItemsItemList = new ArrayList<>();
        List<Card> cardList = mCardDBRepository.getCarts();
        for (int i = 0; i < cardList.size(); i++) {
            LineItemsItem lineItemsItem = new LineItemsItem();
            lineItemsItem.setProductId(cardList.get(i).getProductId());
            lineItemsItem.setQuantity(cardList.get(i).getProductCount());
            lineItemsItemList.add(lineItemsItem);
        }
        return lineItemsItemList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController=Navigation.findNavController(view);
    }

    private void replaceSignUpFragment() {
        mNavController.navigate(R.id.action_ShoppingBagFragment_to_SignUpFragment);
    }


    private void initViews() {
        mBinding.recyclerViewCards.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        List<Product> orderList = getOrders();

        updateUI(mBinding.recyclerViewCards, orderList);

    }

    private List<Product> getOrders() {
        List<Card> cardList = mCardDBRepository.getCarts();
        List<Product> orderList = new ArrayList<>();
        for (int i = 0; i < cardList.size(); i++) {
            Log.d(TAG, "getOrders + product name is :" + cardList.get(i).getProduct().
                    getName());
            orderList.add(cardList.get(i).getProduct());
        }
        return orderList;
    }


    private void updateUI(RecyclerView recyclerView,

                          List<Product> orderList) {

        if (mCardAdapter == null) {
            mCardAdapter = new CardAdapter(getContext(), orderList);
            recyclerView.setAdapter(mCardAdapter);
        } else {
            mCardAdapter.setOrderList(orderList);
            mCardAdapter.notifyDataSetChanged();
        }

    }


}