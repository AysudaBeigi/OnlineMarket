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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("products/")
    Call<List<Product>> getProducts(@QueryMap Map<String, String> map);

    @GET("products/{id}/")
    Call<Product> getProduct(@Path("id") int productId, @QueryMap Map<String, String> map);

    @GET("products/categories/")
    Call<List<Category>> getCategories(@QueryMap Map<String, String> map);

    @GET("products/categories/attributes")
    Call<List<Attribute>> getAttributes(@QueryMap Map<String, String> map);

    @GET("customers")
    Call<List<Customer>> getCustomers(@QueryMap Map<String, String> options);

    @POST("orders")
    Call<Order> postOrder(@Body Order order);


    @FormUrlEncoded
    @POST("customers")
    Call<Customer> postCustomer(@Field("email") String email,
                            @Field("first_name") String first_name,
                            @Field("last_name") String last_name,
                            @Field("username") String username,
                            @QueryMap Map<String, String> options);

   /* @FormUrlEncoded
    @POST("products/reviews")
    Call<Comment> addComment(@Field("product_id") int product_id,@Field("review") String review,
                             @Field("reviewer") String reviewer,@Field("reviewer_email") String reviewer_email,
                             @Field("rating") int rating,@QueryMap Map<String, String> options);

    @GET("coupons")
    Call<List<Coupons>> coupons(@QueryMap Map<String, String> options);
*/


}
