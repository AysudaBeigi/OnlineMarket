package com.example.onlinemarket.network;

import com.example.onlinemarket.model.Attribute;
import com.example.onlinemarket.model.Comment;
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

public interface WooCommerceAPIService {

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


    @POST("customers")
    Call<Customer> postCustomer(@QueryMap Map<String, String> options,
                                @Body Customer customer);


    @POST("orders")
    Call<Order> postOrder(@Body Order order);

    @GET("products/reviews")
    Call<List<Comment>> getComments(@QueryMap Map<String, String> options);


    @GET("products/reviews")
    Call<List<Comment>> getProductComments(@QueryMap Map<String, String> options);


    @FormUrlEncoded
    @POST("products/reviews")
    Call<Comment> postComment(@Field("product_id") int productId,
                              @Field("review") String review,
                              @Field("reviewer_email") String reviewerEmail,
                              @Field("rating") int rating,
                              @QueryMap Map<String, String> options);
   /* @GET("coupons")
    Call<List<Coupons>> coupons(@QueryMap Map<String, String> options);
*/


}
