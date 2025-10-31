package com.example.foodapp.Activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapter.CartListAdapter;
import com.example.foodapp.Helper.ManagementCart;
import com.example.foodapp.Helper.TinyDB;
import com.example.foodapp.Interface.ChangeNumberItemsListener;
import com.example.foodapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeeTxt,taxTxt,deliveryTxt,totalTxt,money;
    ConstraintLayout emptyTxt;
    private  Double tax;
    private ScrollView scrollView;
    private Context context;
    private TinyDB tinyDB;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        CalculateCart();
        bottomNavigation();
        cashOut();
        check();
        /*managementCart.fullNav();*/

    }


    public void cashOut() {
        TextView checkout = findViewById(R.id.checkoutBtn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (managementCart.total() < 1) {
                    startActivity(new Intent(CartListActivity.this, CartListActivity.class));
                    /*Toast.makeText(context, "No Orders Made, Please Add Something Into The Cart", Toast.LENGTH_LONG).show();*/
                    /*There is a problem with the Toast..Please, correct later*/
                } else{
                    startActivity(new Intent(CartListActivity.this, CheckOut.class));
                }
            }
        });
    }
    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout mapsBtn = findViewById(R.id.mapsBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this,MainActivity.class));
            }
        });
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this,MapsActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView4);
        recyclerViewList = findViewById(R.id.cartView);
    }
    private void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(),this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            deliveryTxt.setText("KSHs " + managementCart.deliveryNull());
        }else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    public double CalculateCart() {
        totalFeeTxt.setText("KSHs " + managementCart.itemTotal());
        taxTxt.setText("KSHs " + managementCart.tax());
        deliveryTxt.setText("KSHs " + managementCart.fullDelivery());
        totalTxt.setText("KSHs " + managementCart.total());
        System.out.println(managementCart.total());
        return managementCart.total();
    }
    public TextView check() {
        TextView moneyMaker = findViewById(R.id.checkoutBtn);
        return moneyMaker;
    }
}