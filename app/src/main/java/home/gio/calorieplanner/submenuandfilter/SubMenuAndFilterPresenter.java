package home.gio.calorieplanner.submenuandfilter;


import java.lang.ref.WeakReference;
import java.util.List;

import home.gio.calorieplanner.models.Category;

public class SubMenuAndFilterPresenter implements ISubMenuAndFilterPresenter {
    private SubMenuAndFilter model;
    private WeakReference<ISubMenuAndFilterView> mView;

    public SubMenuAndFilterPresenter(ISubMenuAndFilterView view) {
        this.model = new SubMenuAndFilter();
        this.mView = new WeakReference<ISubMenuAndFilterView>(view);
    }

    private ISubMenuAndFilterView getView() throws NullPointerException {
        if (mView != null) {
            return mView.get();
        } else {
            throw new NullPointerException("View in unavailable");
        }
    }
    public List<Category> getCategories(){
        return model.getCategories();
    }

}
