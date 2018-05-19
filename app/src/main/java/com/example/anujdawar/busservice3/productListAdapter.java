package com.example.anujdawar.busservice3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class productListAdapter extends BaseAdapter{

    private Context mContext;
    private List<product> mProductList;
    TextView tvBusType, tvBusCompany, tvBusSource, tvBusDestination, tvBusFare;

    public productListAdapter(Context mContext, List<product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.text_views, null);

        tvBusCompany = (TextView) v.findViewById(R.id.company_name_value);
        tvBusType = (TextView) v.findViewById(R.id.busType);
        tvBusFare = (TextView) v.findViewById(R.id.busFare);
        tvBusSource = (TextView) v.findViewById(R.id.busFrom);
        tvBusDestination = (TextView) v.findViewById(R.id.busTo);

        tvBusCompany.setText(mProductList.get(i).getMyBusCompany());
        tvBusType.setText(mProductList.get(i).getMyBusType());
        tvBusFare.setText(mProductList.get(i).getMyBusFare());
        tvBusSource.setText(mProductList.get(i).getMyBusSource());
        tvBusDestination.setText(mProductList.get(i).getMyBusDestination());

        v.setTag(mProductList.get(i).getID());

        return v;
    }
}
