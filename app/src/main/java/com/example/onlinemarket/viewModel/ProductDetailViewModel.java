package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CardDBRepository;
import com.example.onlinemarket.data.repository.CommentRepository;
import com.example.onlinemarket.data.repository.CustomerDBRepository;
import com.example.onlinemarket.utils.OnlineMarketPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;

public class ProductDetailViewModel  extends AndroidViewModel {
    private CardDBRepository mCardDBRepository;
    private CustomerDBRepository mCustomerDBRepository;
    private CommentRepository mCommentRepository;
    private Product mProduct;

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        mCardDBRepository = CardDBRepository.getInstance(application);
        mCustomerDBRepository=CustomerDBRepository.getInstance(application);
        mCommentRepository=CommentRepository.getInstance(application);
        mProduct= OnlineMarketPreferences.getInstance(application).getUserSelectedProduct();

    }

    public Product getProduct() {
        return mProduct;
    }
    public Customer getCustomer(){
        return mCustomerDBRepository.getCustomer();
    }
    public MutableLiveData<List<Comment>> getProductCommentsLiveData(){
       return   mCommentRepository.getProductCommentsLiveData();
    }
    public void setProductComments(int productId){
        mCommentRepository.setProductComments(productId);
    }



    public boolean isProductInCart() {

        Card card = new Card(mProduct, mProduct.getId(), 1);
        return mCardDBRepository.getCarts().contains(card);
    }

    public void addTooCard() {
        Card card = new Card(mProduct, mProduct.getId(), 1);
        mCardDBRepository.insertCart(card);

    }
    public String getInformation() {
        String description = mProduct.getDescription();
        if (description.equals(null))
            return description;
        Document document = Jsoup.parse(description);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        return Jsoup.clean(s, "", Whitelist.none(),
                new Document.OutputSettings().prettyPrint(false));

    }

}
