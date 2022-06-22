package com.example.thuchanh2.view.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchanh2.R;
import com.example.thuchanh2.model.Ticket;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("NotifyDataSetChanged")
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private final ArrayList<Ticket> list = new ArrayList<>();
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }


    public void remove(Ticket ticket, int position) {
        list.remove(ticket);
        notifyItemRemoved(position);
    }

    public void update(Ticket ticket, int position) {
        list.set(position, ticket);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] departs = {"Hà nội", "Đà Nẵng", "Huế", "Tp HCM", "Hải Phòng"};
        Ticket ticket = list.get(position);
        holder.name.setText("#" + ticket.getId() + " " + ticket.getName());
        holder.date.setText("Ngày: " + ticket.getDateStr());
        holder.depart.setText("Khởi hành: " + departs[ticket.getDepart()] + " - giá: " + ticket.getPrice());
        holder.hasPackage.setText("Ký gửi: " + (ticket.getHasPackage() ? "có" : "không"));
        holder.delete.setOnClickListener(view -> {
            int index = holder.getLayoutPosition();
            if (itemListener != null) {
                itemListener.onDelete(ticket, index);
            }
        });
        holder.edit.setOnClickListener(view -> {
            int index = holder.getLayoutPosition();
            if (itemListener != null) {
                itemListener.onEdit(ticket, index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Ticket> tickets) {
        list.clear();
        list.addAll(tickets);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, date, depart, hasPackage;
        private final ImageButton delete, edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_date);
            name = itemView.findViewById(R.id.tv_name);
            depart = itemView.findViewById(R.id.tv_depart);
            hasPackage = itemView.findViewById(R.id.tv_package);
            delete = itemView.findViewById(R.id.btn_delete);
            edit = itemView.findViewById(R.id.btn_edit);
        }
    }

    interface ItemListener {
        void onDelete(Ticket ticket, int position);

        void onEdit(Ticket ticket, int position);
    }
}