package home.gio.calorieplanner.main;


import android.content.Context;
import android.content.SharedPreferences;

import android.os.Environment;

import android.util.Log;
import android.util.SparseArray;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import home.gio.calorieplanner.models.Product;
import home.gio.calorieplanner.models.RetailChain;

public class Main implements IMainModel {
    public RetailChain retailChain = new RetailChain();
    public List<RetailChain> retailChainList = new ArrayList<>();
    public static List<RetailChain> outRetailChainList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private SharedPreferences prefs = null;

    @Override
    public void parseGoodwillSakvebiProductebiHTML(final Context context) {
        retailChain.setName("Goodwill");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                Document docDetails = null;
                Elements detailsLink = null;
                String tempString = null;
                String baseURL = "http://goodwilldelivery.ge/";
                try {
                    doc = Jsoup.connect("http://goodwilldelivery.ge/category.aspx?id=4").get();//sakvebi produqti
                    Elements subLinks = doc.select("div[class=\"sub_links\"]");
                    Elements categories = doc.select("div[id=\"ProductMenu\"]").select("a[class=\"sub_menu\"]");
                    Elements children = subLinks.get(10).children();
                    for (int subMenuIndex = 0; subMenuIndex < children.size(); subMenuIndex++) {
                        Elements hrefs = children.select("a[href]");
                        StringBuilder link = new StringBuilder(baseURL + "category.aspx?id=171");//hrefs.get(subMenuIndex).select("a[href]").attr("href")
                        doc = Jsoup.connect(link.toString()).get();
                        Elements pages = doc.select("div[class=\"pager\"]");
                        int pagesQuantity = pages.get(0).text().length();
                        for (int i = 1; i <= pagesQuantity; i++) {
                            tempString = "&page=" + String.valueOf(i);
                            link.append(tempString);
                            doc = Jsoup.connect(link.toString()).get();
                            Elements links = doc.select("div[class=\"ProductContainer\"]");
                            for (int j = 0; j < links.size(); j++) {//getting picture and name of product
                                Product tempProduct = new Product();
                                tempProduct.setCategory(categories.get(10).text());//category name
                                tempProduct.setSubMenu(hrefs.get(2).text());//submenu name
                                tempProduct.setImageURL(links.get(j).select("a[href]").select("img").attr("src"));//picture
                                tempProduct.setName(links.get(j).select("a[href]").text());//name
                                tempProduct.setPrice(links.get(j).select("strong").text());//price
                                docDetails = Jsoup.connect("http://goodwilldelivery.ge/" + links.get(j).select("a").attr("href")).get();//details link
                                detailsLink = docDetails.select("div[class=\"AdvancedControls\"]");
                                tempProduct.setDetails(detailsLink.text());//Product Details
                                tempProduct.setFat(Integer.parseInt(getFat(getNutritionFacts(detailsLink.text()))));
                                tempProduct.setProtein(Integer.parseInt(getProtein(getNutritionFacts(detailsLink.text()))));
                                tempProduct.setCarbohydrates(Integer.parseInt(getCarbs(getNutritionFacts(detailsLink.text()))));
                                tempProduct.setCalories(Integer.parseInt(getCalories(getNutritionFacts(detailsLink.text()))));


                                productList.add(tempProduct);
                            }
                            int k = link.indexOf(tempString);
                            if (k != -1) {
                                link.delete(k, k + tempString.length());
                            }
                        }
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("Goodwill").child(productList.get(productList.size() - 1).getCategory().replaceAll("[#$.\\]\\[]", " ")).child(productList.get(productList.size() - 1).getSubMenu().replaceAll("[#$.\\]\\[]", " ")).setValue(productList);
                        productList.clear();

                    }
                } catch (Exception ex) {
                    Log.d("network", "error in parsing" + ex.toString());
                }
            }
        }).start();
    }

    @Override
    public void loadDataFromDatabase(Context context) {
        prefs = context.getSharedPreferences("home.gio.calorieplanner", context.MODE_PRIVATE);
        if (prefs.getBoolean("firstRun", true)) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            prefs.edit().putBoolean("firstRun", false).apply();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot categorySnaps : dataSnapshot.getChildren()) {
                        RetailChain retailChain = new RetailChain();
                        retailChain.setName(categorySnaps.getKey());
                        for (DataSnapshot subMenuSnaps : categorySnaps.getChildren()) {
                            for (DataSnapshot itemSnaps : subMenuSnaps.getChildren()) {
                                for (DataSnapshot productSnaps : itemSnaps.getChildren()) {
                                    Product product = productSnaps.getValue(Product.class);
                                    productList.add(product);
                                }
                            }
                        }
                        retailChain.setProducts(productList);
                        retailChainList.add(retailChain);
                    }

                    writeInFile();
                    outRetailChainList = retailChainList;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("database error: ", "onCancelled", databaseError.toException());
                }
            });
        } else {
            readFile();

        }
    }

    private void writeInFile() {
        ObjectOutput out;
        try {
            File outFile = new File(Environment.getExternalStorageDirectory(), "appSavedListData.data");
            out = new ObjectOutputStream(new DeflaterOutputStream(new FileOutputStream(outFile)));
            out.writeObject(retailChainList);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readFile() {
        ObjectInput in;
        try {
            File inFile = new File(Environment.getExternalStorageDirectory(), "appSavedListData.data");
            ObjectInputStream ois = new ObjectInputStream(new InflaterInputStream(new FileInputStream(inFile)));
            outRetailChainList = (List<RetailChain>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getFat(String details) {
        int index = details.indexOf("ცხიმი");
        if (index == -1)
            return "0";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(details.substring(index, index + 15));
        matcher.find();
        return matcher.group();
    }

    public static String getPrice(String details) {
        int index = details.indexOf("ფასი:");
        if (index == -1)
            return "0";
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(details.substring(index, index + 10));
        matcher.find();
        return matcher.group();
    }

    public static String getProtein(String details) {
        int index = details.indexOf("ცილა");
        if (index == -1)
            index = details.indexOf("ცილებ");
        if (index == -1)
            return "0";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(details.substring(index, index + 15));
        matcher.find();
        return matcher.group();
    }

    public static String getCarbs(String details) {
        int index = details.indexOf("ნახშირწყ");
        if (index == -1)
            return "0";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(details.substring(index, index + 17));
        matcher.find();
        return matcher.group();
    }

    public static String getCalories(String details) {
        int index = details.indexOf("ენერგეტიკული ღირებულება");
        if (index == -1)
            return "0";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(details.substring(index, index + 29));
        matcher.find();
        return matcher.group();
    }
    public static String getMyCalories(String details) {
        int index = details.indexOf("კალორიები:");
        if (index == -1)
            return "0";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(details.substring(index, index + 15));
        matcher.find();
        return matcher.group();
    }

    private String getNutritionFacts(String details) {
        int index = details.indexOf("კვებითი ღირებულება");
        if (index == -1)
            index = details.indexOf("კვებიტი ღირებულება");
        if (index == -1)
            return "0";
        return details.substring(index, details.length());
    }

}
