package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.model.order.LineItemsItem;
import com.example.onlinemarket.data.model.order.Order;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CardDBRepository;
import com.example.onlinemarket.data.repository.CustomerDBRepository;
import com.example.onlinemarket.data.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBagViewModel extends AndroidViewModel {
    private CustomerDBRepository mCustomerDBRepository;
    private CardDBRepository mCardDBRepository;
    private OrderRepository mOrderRepository;

    public ShoppingBagViewModel(@NonNull Application application) {

        super(application);
        mCustomerDBRepository = CustomerDBRepository.getInstance(application);
        mCardDBRepository = CardDBRepository.getInstance(application);
        mOrderRepository = OrderRepository.getInstance(application);

    }

    public List<Product> getOrders() {
        List<Card> cardList = mCardDBRepository.getCarts();
        List<Product> orderList = new ArrayList<>();
        for (int i = 0; i < cardList.size(); i++) {
            orderList.add(cardList.get(i).getProduct());
        }
        return orderList;
    }

    public Customer getCustomer() {
        return mCustomerDBRepository.getCustomer();

    }

    public MutableLiveData<Order> getPostOrderMutableLiveData() {
        return mOrderRepository.getPostOrderMutableLiveData();
    }

    public void setPostOrderLiveData() {

        mOrderRepository.setPostOrderLiveData(getOrder());
    }


    private Order getOrder() {
        List<LineItemsItem> lineItemsItemList = getLineItemsItemList();
        Order order = new Order();
        order.setCustomerId(mCustomerDBRepository.getCustomer().getId());
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
}
