package home.gio.calorieplanner.productcatalog;


import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

import home.gio.calorieplanner.Constants;
import home.gio.calorieplanner.main.Main;
import home.gio.calorieplanner.models.Category;
import home.gio.calorieplanner.models.Product;
import home.gio.calorieplanner.models.RetailChain;
import home.gio.calorieplanner.models.SubMenu;
import java8.util.J8Arrays;
import java8.util.stream.RefStreams;

public class ProductCatalog implements IProductCatalogModel {

    public List<SubMenu> getSubMenus(List<Product> products, String category) {
        List<SubMenu> tempList = new ArrayList<>();
        String lastSubMenu = "";
        for (Product singleProduct : products) {
            if (singleProduct.getCategory().equals(category)) {
                if (!singleProduct.getSubMenu().equals(lastSubMenu)) {
                    lastSubMenu = singleProduct.getSubMenu();
                    tempList.add(new SubMenu(lastSubMenu));
                }
            }
        }
        return tempList;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String lastCategory = "";
        for (RetailChain retailChain : Main.outRetailChainList) {
            for (Product product : retailChain.getProducts()) {
                if (!lastCategory.equals(product.getCategory())) {
                    lastCategory = product.getCategory();
                    categories.add(new Category(product.getCategory(), getSubMenus(retailChain.getProducts(), lastCategory)));
                }
            }

        }
        return categories;
    }

    public String identifySubMenu(String group, int index) {
        List<Product> prod = StreamSupport
                .stream(Main.outRetailChainList.get(Constants.GOODWILL).getProducts())
                .filter(item -> item.getCategory().equals(group))
                .collect(Collectors.toList());

        return null;
    }
}
