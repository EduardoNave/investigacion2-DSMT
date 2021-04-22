package sv.edu.udb.investigacion2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sv.edu.udb.investigacion2.data.Product;

public class ProductAdapter extends ArrayAdapter<Product> {
    List<Product> products;
    private Activity context;

    public ProductAdapter(@NonNull Activity context, @NonNull List<Product> products){
        super(context, R.layout.product_layout, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview = null;
        if (view == null)
            rowview = layoutInflater.inflate(R.layout.product_layout, null);
        else
            rowview = view;

        TextView tvCode = rowview.findViewById(R.id.tvCode);
        TextView tvDescription = rowview.findViewById(R.id.tvDescrip);
        TextView tvPrice = rowview.findViewById(R.id.tvPrice);

        tvCode.setText("Código: " + products.get(position).getCode());
        tvDescription.setText("Descripción: " + products.get(position).getDescription());
        tvPrice.setText("Precio: $" + products.get(position).getPrice().toString());

        return rowview;
    }
}
