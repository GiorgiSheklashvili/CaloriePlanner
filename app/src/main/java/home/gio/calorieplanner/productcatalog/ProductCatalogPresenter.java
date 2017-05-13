package home.gio.calorieplanner.productcatalog;


import java.lang.ref.WeakReference;

public class ProductCatalogPresenter implements IProductCatalogPresenter {
    private ProductCatalog model;
    private WeakReference<IProductCatalogView> mView;

    public ProductCatalogPresenter(IProductCatalogView view) {
        this.model = new ProductCatalog();
        this.mView = new WeakReference<IProductCatalogView>(view);
    }

    private IProductCatalogView getView() throws NullPointerException {
        if (mView != null) {
            return mView.get();
        } else {
            throw new NullPointerException("View in unavailable");
        }
    }
}
