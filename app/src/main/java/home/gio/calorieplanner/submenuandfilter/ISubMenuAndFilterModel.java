package home.gio.calorieplanner.submenuandfilter;


import java.util.List;

import home.gio.calorieplanner.models.Category;
import home.gio.calorieplanner.models.Product;
import home.gio.calorieplanner.models.SubMenu;

public interface ISubMenuAndFilterModel {
    List<SubMenu> getSubMenus(List<Product> products, String category);
    List<Category> getCategories();
}
