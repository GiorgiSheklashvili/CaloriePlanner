package home.gio.calorieplanner.productcatalog;


import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.Constants;
import home.gio.calorieplanner.main.Main;
import home.gio.calorieplanner.models.CategoryForProducts;
import home.gio.calorieplanner.models.Product;

public class ProductCatalog implements IProductCatalogModel {
    public List<CategoryForProducts> getCategories(ArrayList<String> listOfCategories) {
        List<CategoryForProducts> categoryList = new ArrayList<>();
        for (String category : listOfCategories) {
            List<Product> products = new ArrayList<>();
            for (Product product : Main.outRetailChainList.get(Constants.GOODWILL).getProducts()) {
                if (product.getSubMenu().equals(category)) {
                    products.add(product);
                }
            }
            CategoryForProducts categoryForProducts = new CategoryForProducts(category, products);
            categoryList.add(categoryForProducts);
        }
        return categoryList;
    }
}
