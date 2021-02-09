package com.example.onlinemarket.network;

import com.example.onlinemarket.model.Attribute;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.order.Order;
import com.example.onlinemarket.model.product.Category;
import com.example.onlinemarket.model.product.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MarketService {

    @GET("products/")
    Call<List<Product>> getProducts(@QueryMap Map<String, String> map);

    @GET("products/{id}/")
    Call<Product> getProduct(@Path("id") int productId, @QueryMap Map<String, String> map);

    @GET("products/categories/")
    Call<List<Category>> getCategories(@QueryMap Map<String, String> map);

    @GET("products/categories/attributes")
    Call<List<Attribute>> getAttributes(@QueryMap Map<String, String> map);

    /*@FormUrlEncoded
    @POST("customers?consumer_key=ck_f025265e3479f7bee8e93bffe5685517b93ec27d & " +
            "consumer_secret=cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0")
    Call<Customer> postCustomer(@Field("email") String email);
*/
    @GET("customers")
    Call<List<Customer>> getCustomers(@QueryMap Map<String, String> options);

    @POST("orders")
    Call<Order> postOrder(@Body Order order);

    @POST("customers")
    Call<Customer> postCustomers(@QueryMap Map<String, String> options,
                                 @Body Customer customer );
    /*@GET("coupons")
    Call<List<Coupon>> getCoupons(@QueryMap Map<String, String> options);
*/


}
