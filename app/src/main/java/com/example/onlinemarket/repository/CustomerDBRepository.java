package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.ICustomerDatabaseDAO;
import com.example.onlinemarket.database.OnlineMarketDatabase;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.network.APIService;
import com.example.onlinemarket.network.NetworkParams;
import com.example.onlinemarket.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDBRepository implements ICustomerRepository {

    private static CustomerDBRepository sInstance;

    private ICustomerDatabaseDAO mICustomerDatabaseDAO;
    private Context mContext;

    public static CustomerDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CustomerDBRepository(context);
        return sInstance;
    }

    private APIService mAPIService;


    private CustomerDBRepository(Context context) {
        mAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(APIService.class);

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

        mAPIService.postCustomer(customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getUsername(),
                NetworkParams.getBaseQuery()).
                enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        insertCustomer(response.body());
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                    }
                });

    }

    private void insertCustomer(Customer customer) {
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