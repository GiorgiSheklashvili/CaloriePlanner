package home.gio.calorieplanner.shoppinglist;


import java.lang.ref.WeakReference;


public class ShoppingListPresenter implements IShoppingListPresenter {
    public ShoppingList model;
    private WeakReference<IShoppingListView> mView;

    public ShoppingListPresenter(IShoppingListView mView) {
        this.mView = new WeakReference<>(mView);
        this.model = new ShoppingList();
    }

    public IShoppingListView getView() throws NullPointerException {
        if (mView != null) {
            return mView.get();
        } else {
            throw new NullPointerException("view in unavailable");
        }
    }
}
