package sv.edu.udb.investigacion2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sv.edu.udb.investigacion2.data.Product;

public class ProductsActivity extends AppCompatActivity {
    public static FirebaseDatabase dataBase = FirebaseDatabase.getInstance();
    public static DatabaseReference refProducts = dataBase.getReference("products");

    //ordenar según codigo
    Query sortQuery = refProducts.orderByChild("code");

    List<Product> products;
    ListView listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        init();
    }

    private void init() {
        FloatingActionButton fab_add = findViewById(R.id.fab_add);
        listProducts = findViewById(R.id.ListProducts);

        //clic en listview para editar registro
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), AddProductActivity.class);

                intent.putExtra("accion", "e"); //Editar
                intent.putExtra("key", products.get(position).getKey());
                intent.putExtra("code", products.get(position).getCode());
                intent.putExtra("description", products.get(position).getDescription());
                intent.putExtra("price", products.get(position).getPrice());
                startActivity(intent);
            }
        });

        //longclic en listview para eliminar
        listProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l){
                //Cuadro de diálogo de confirmación
                AlertDialog.Builder ad = new AlertDialog.Builder(ProductsActivity.this);
                ad.setMessage("¿Está seguro de eliminar este producto?")
                        .setTitle("Confirmar acción");
                ad.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductsActivity.refProducts
                                .child(products.get(position).getKey()).removeValue();
                        Toast.makeText(ProductsActivity.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
                ad.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProductsActivity.this,
                                "Acción cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                ad.show();
                return true;
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clic en botón flotante para agregar registro
                Intent i = new Intent(getBaseContext(), AddProductActivity.class);
                i.putExtra("accion", "a"); //agregar
                i.putExtra("key", "");
                i.putExtra("code", "");
                i.putExtra("description", "");
                i.putExtra("price", 0.00);
                startActivity(i);
            }
        });

        products = new ArrayList<>();
        sortQuery.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                //Se ejecuta al cambio en la db
                products.removeAll(products);
                for(DataSnapshot dato : dataSnapshot.getChildren()){
                    Product product = dato.getValue(Product.class);
                    product.setKey(dato.getKey());
                    products.add(product);
                }

                ProductAdapter adapter = new ProductAdapter(ProductsActivity.this, products);
                listProducts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }
}
