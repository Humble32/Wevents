package com.example.commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commerce.R;
import com.example.commerce.domain.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context applicationContext;
    List<Address> AddressList;
    private RadioButton SelectedRadioButton;
    SelectedAddress selectedAddress;
    public AddressAdapter(Context applicationContext, List<Address> AddressList,SelectedAddress SelectedAddress) {
        this.applicationContext=applicationContext;
        this.AddressList=AddressList;
        this.selectedAddress=selectedAddress;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(applicationContext).inflate(R.layout.single_address_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.Address.setText((AddressList.get(position).getAddress()));
        holder.Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Address address:AddressList){
                    address.setSelected(false);
                }
                AddressList.get(position).setSelected(true);
                if (SelectedRadioButton!=null){
                    SelectedRadioButton.setChecked(false);
                }
                SelectedRadioButton=(RadioButton) v;
                SelectedRadioButton.setChecked(true);
                selectedAddress.setAddress(AddressList.get(position).getAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return AddressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Address;
        private RadioButton Radio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address=itemView.findViewById(R.id.address_add);
            Radio=itemView.findViewById(R.id.select_address);

        }
    }
    public interface SelectedAddress{
        public void setAddress(String s);
    }
}
