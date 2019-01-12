package com.example.pointme.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.pointme.R;
import com.example.pointme.adapters.ProvidersAdapter;
import com.example.pointme.classes.ProvidersInfo;

import java.util.ArrayList;
import java.util.List;

public class ListOfServiceProviders extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_service_providers);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbarLayout);

        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty())
        {
            collapsingToolbar.setTitle(bundle.getString("title"));

        }
      //  collapsingToolbar.setTitle("Pointme");

        RecyclerView list = (RecyclerView) findViewById(R.id.cardList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        // Create car recycler view data adapter with car item list.
        ProvidersAdapter providersAdapter = new ProvidersAdapter(createList(10));
        // Set data adapter.
        list.setAdapter(providersAdapter);
    }

    private List<ProvidersInfo> createList(int size) {

        List<ProvidersInfo> result = new ArrayList<ProvidersInfo>();
        for (int i=1; i <= size; i++) {
            ProvidersInfo info = new ProvidersInfo();
            info.setName(ProvidersInfo.getNamePrefix()+ i);
            info.setSurname (ProvidersInfo.getSurnamePrefix() + i);
            info.setEmail (ProvidersInfo.getEmailPrefix() + i + "@test.com");

            result.add(info);

        }

        return result;
    }

}
