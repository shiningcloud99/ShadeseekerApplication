package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Product;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_grid_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.descriptionTextView.setText(product.getDescription());
        holder.shadeTextView.setText(product.getShade());
        holder.toneTextView.setText(product.getTone());
        holder.codeTextView.setText(product.getCode());
        Picasso.get().load(product.getImageUrl()).into(holder.productImageView);
    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, descriptionTextView, shadeTextView, toneTextView, codeTextView;
        ImageView productImageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            shadeTextView = itemView.findViewById(R.id.shadeTextView);
            toneTextView = itemView.findViewById(R.id.toneTextView);
            codeTextView = itemView.findViewById(R.id.codeTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
        }
    }
}
