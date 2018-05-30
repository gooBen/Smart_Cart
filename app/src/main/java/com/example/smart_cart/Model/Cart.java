package com.example.smart_cart.Model;

import java.util.ArrayList;

public class Cart {
    ArrayList<Product> CartList = new ArrayList<Product>();

    public Cart(){
    }

    public ArrayList<String> getProductsName(){
        ArrayList<String> names = new ArrayList<String>();
        for(Product product:CartList)
        {
            names.add(product.getName());
        }
        return names;
    }

    public void shoppeOneProduct(Product product){
        CartList.add(product);
    }

    public double totalCost(){
        double cost = 0;
        if(ArrayCount() != 0){
            for(Product product:CartList){
                cost = cost + product.getPrice();
            }
        }
        return cost;
    }

   public int ArrayCount() {
        return CartList.size();
    }

    public String getBarByIndex(int position){
        return CartList.get(position).getBar();
    }

    public void deleteFromCart(Product product){ CartList.remove(product);}

    public Product getProductByIndex(int position){return CartList.get(position);}
}
