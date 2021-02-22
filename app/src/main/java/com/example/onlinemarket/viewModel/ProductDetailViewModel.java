package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CardDBRepository;
import com.example.onlinemarket.data.repository.CommentRepository;
import com.example.onlinemarket.data.repository.CustomerDBRepository;
import com.example.onlinemarket.data.repository.ProductRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;

public class ProductDetailViewModel extends AndroidViewModel {
    private CardDBRepository mCardDBRepository;
    private CustomerDBRepository mCustomerDBRepository;
    private CommentRepository mCommentRepository;
    private ProductRepository mProductRepository;

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        mCardDBRepository = CardDBRepository.getInstance(application);
        mCustomerDBRepository = CustomerDBRepository.getInstance(application);
        mCommentRepository = CommentRepository.getInstance(application);
        mProductRepository = ProductRepository.getInstance(application);

    }

    public Product getProduct() {
        return mProductRepository.getUserSelectedProduct();
    }

    public void setProduct(Product product) {
        mProductRepository.setUserSelectedProduct(product);
    }

    public String getPrice() {
        return mProductRepository.getUserSelectedProductPrice();
    }

    public String getRegularPrice() {
        return mProductRepository.getUserSelectedProductRegularPrice();
    }

    public String getName() {
        return mProductRepository.getUserSelectedProductName();
    }

    public List<Image> getImages() {
        return mProductRepository.getUserSelectedProductImages();
    }

    public boolean isProductInCardAndPriceNotNull() {
        return !isProductInCard() &&
                mProductRepository.getUserSelectedProductPrice() != null;
    }

    public boolean isProductInCard() {

        return mCardDBRepository.isProductInCard(mProductRepository.getUserSelectedProduct());
    }


    public void addTooCard() {
        mCardDBRepository.addProductTooCard(mProductRepository.getUserSelectedProduct());

    }

    public String getInformation() {
        String description =
                mProductRepository.getUserSelectedProduct().getDescription();
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


    public Customer getCustomer() {
        return mCustomerDBRepository.getCustomer();
    }

    public MutableLiveData<List<Comment>> getProductCommentsLiveData() {
        return mCommentRepository.getProductCommentsLiveData();
    }

    public void setProductComments() {

        mCommentRepository.setProductComments(mProductRepository.getUserSelectedProductId());
    }
}
