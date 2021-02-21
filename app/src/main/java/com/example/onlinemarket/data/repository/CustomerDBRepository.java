package com.example.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;
import com.example.onlinemarket.data.room.ICustomerDatabaseDAO;
import com.example.onlinemarket.data.room.OnlineMarketDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDBRepository implements ICustomerRepository {

    private static CustomerDBRepository sInstance;

    private ICustomerDatabaseDAO mICustomerDatabaseDAO;
    private WooCommerceAPIService mWooCommerceAPIService;
    private Context mContext;
    public static String TAG = "OnlineMarket";


    public static CustomerDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CustomerDBRepository(context);
        return sInstance;
    }



    private CustomerDBRepository(Context context) {
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);

        mContext = context.getApplicationContext();
        OnlineMarketDatabase onlineMarketDatabase = Room.databaseBuilder(mContext,
                OnlineMarketDatabase.class,
                "onlineMarket.db")
                .allowMainThreadQueries()
                .build();
        mICustomerDatabaseDAO = onlineMarketDatabase.
                getCustomerDatabaseDAO();
    }


    public void postCustomer(Customer customer, CustomerDBRepository.CustomerCallback
            customerCallbacks) {

        mWooCommerceAPIService.postCustomer(
                NetworkParams.getBaseQuery(),customer).
                enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {

                        Customer customer= response.body();
                        customerCallbacks.onItemResponse(customer);

                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);

                    }
                });

    }

    public void insertCustomer(Customer customer) {
        mICustomerDatabaseDAO.insertCustomer(customer);

    }

    @Override
    public void updateCustomer(Customer customer) {
        mICustomerDatabaseDAO.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        mICustomerDatabaseDAO.deleteCustomer(customer);
    }

    @Override
    public Customer getCustomer() {
        return mICustomerDatabaseDAO.getCustomer();
    }


    public interface CustomerCallback {
        void onItemResponse(Customer customer);
    }

}
