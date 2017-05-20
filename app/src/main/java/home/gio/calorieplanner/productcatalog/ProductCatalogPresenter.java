package home.gio.calorieplanner.productcatalog;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.models.CategoryForProducts;

public class ProductCatalogPresenter implements IProductCatalogPresenter {
    private ProductCatalog model;
    private WeakReference<IProductCatalogView> mView;

    public ProductCatalogPresenter(IProductCatalogView mView) {
        this.mView = new WeakReference<>(mView);
        this.model = new ProductCatalog();
    }

    private IProductCatalogView getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else {
            throw new NullPointerException("view in unavailable");
        }

    }

    @Override
    public List<CategoryForProducts> getCategories(ArrayList<String> listOfCategories) {
        return model.getCategories(listOfCategories);
    }
}
