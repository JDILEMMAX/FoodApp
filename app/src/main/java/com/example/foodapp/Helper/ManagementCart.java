package com.example.foodapp.Helper;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Activity.CartListActivity;
import com.example.foodapp.Domain.FoodDomain;
import com.example.foodapp.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;
    private CartListActivity cartListActivity;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size();/*Checks number of items(if there was an item initially)*/ i++)
            /*This line of code enables us to run the program but with the exception that everytime something is added,
            it is subjected to the validation that it will always be less than the initial value*/
        /*Thus for the values that meet the validation checks, the program can be executed*/{
            if(listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {/*Did NOT Exist/Cart was empty initially*/
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else {/*Now items existed thus new value is added onto the initial one*/
            listFood.add(item);
        }
        tinyDB.putListObject("CartList",listFood);
        Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<FoodDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void plusNumberFood(ArrayList<FoodDomain>listFood, int position, ChangeNumberItemsListener changeNumberItemListener) {
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList",listFood);
        changeNumberItemListener.changed();
    }
    public void minusNumberFood(ArrayList<FoodDomain>listFood, int position, ChangeNumberItemsListener changeNumberItemListener) {
        if (listFood.get(position).getNumberInCart() == 1) {
            listFood.remove(position);
        }else{
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList",listFood);
        changeNumberItemListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<FoodDomain> listFood = getListCart();
        double fee = 0;
                for (int i = 0; i < listFood.size(); i++) {
                    fee = fee + (listFood.get(i).getFee() * listFood.get(i).getNumberInCart());
                }
        return fee;
    }

    public Double delivery() {
        double delivery = 100;
        return delivery;
    }
    public Double deliveryNull() {
        double delivery = 0;
        return delivery;
    }
    public Double itemTotal() {
        double itemTotal = Math.round(getTotalFee() * 100) /100;
        return itemTotal;
    }
    public Double tax() {
        double percentTax = 0.02;

        double tax = Math.round((getTotalFee() * percentTax) * 100) /100;
        return tax;
    }
    public Double total() {
        double delivery=100;
        double tax = tax();

        double total = Math.round((getTotalFee() + tax + fullDelivery()) * 100) /100;
        return total;
    }
    public Double fullDelivery() {
        double Delivery;
        if(getListCart().isEmpty()) {
            Delivery = deliveryNull();
        }else {
            Delivery = delivery();
        }
        return Delivery;
    }
    public void fullNav() {
        if(total() == 0.0) {
            cartListActivity.check().setVisibility(View.GONE);
        }else{
            cartListActivity.check().setVisibility(View.VISIBLE);
        }
    }
}
