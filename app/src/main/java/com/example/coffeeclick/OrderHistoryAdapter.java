package com.example.coffeeclick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeclick.models.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderHistoryAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.tvOrderId.setText("Order #" + order.getId().substring(0, 8));
        holder.tvUsername.setText("Customer: " + order.getUsername());
        holder.tvTotal.setText("Total: LKR " + order.getTotalAmount());
        
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        holder.tvDate.setText(sdf.format(new Date(order.getOrderDate())));
        
        holder.tvStatus.setText(order.getStatus().toUpperCase());
        
        // Build items summary
        StringBuilder items = new StringBuilder();
        if (order.getItems() != null) {
            for (CartItem item : order.getItems()) {
                items.append(item.getCoffee().getName())
                     .append(" x")
                     .append(item.getQuantity())
                     .append("\n");
            }
        }
        holder.tvItems.setText(items.toString().trim());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvUsername, tvTotal, tvDate, tvStatus, tvItems;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvItems = itemView.findViewById(R.id.tvItems);
        }
    }
}
