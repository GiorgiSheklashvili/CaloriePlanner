package home.gio.calorieplanner.productcatalog;


import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.models.CategoryForProducts;

public interface IProductCatalogPresenter {
    List<CategoryForProducts> getCategories(ArrayList<String> listOfCategories);
    List<CategoryForProducts> searchedResults(String word);
}
