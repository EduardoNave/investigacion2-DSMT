package sv.edu.udb.investigacion2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import sv.edu.udb.investigacion2.data.Product;

public class AddProductActivity extends AppCompatActivity {
    EditText edtCode, edtDescription, edtPrice;
    String key="", code="", description="", accion="";
    Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
    }

    private void init() {
        edtCode = findViewById(R.id.edtCode);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);

        //Obtención de datos de la actividad anterior
        Bundle datos = getIntent().getExtras();
        key = datos.getString("key");
        code = datos.getString("code");
        description = datos.getString("descrip");
        price = datos.getDouble("price");
        accion = datos.getString("accion");
        edtCode.setText(code);
        edtDescription.setText(description);
        edtPrice.setText("");
    }

    public void saveProduct(View v){
        String code = edtCode.getText().toString();
        String description = edtDescription.getText().toString();
        Double price = Double.parseDouble(edtPrice.getText().toString());

        //Instancia de producto
        Product product = new Product(code, description, price);
        if(accion.equals("a")){ //agregar
            ProductsActivity.refProducts.push().setValue(product);
        }
        else{ //editar (envia los valores del objeto en el arreglo del key seleccionado
            ProductsActivity.refProducts.child(key).setValue(product);
        }
        finish();
    }

    public void cancel(View v){
        finish();
    }

    public void ClearPrice(View view) {
        edtPrice.setText("");
    }
}
