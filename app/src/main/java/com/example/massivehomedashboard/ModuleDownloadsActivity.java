package com.example.massivehomedashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.massivehomedashboard.db.DbCategorias;
import com.example.massivehomedashboard.db.DbGalleryProducts;
import com.example.massivehomedashboard.db.DbProductos;
import com.example.massivehomedashboard.tools.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModuleDownloadsActivity extends AppCompatActivity {

    CardView cardOptDownloadProducts,cardOptDownloadCategories;
    DbCategorias oDbCategorias = new DbCategorias(ModuleDownloadsActivity.this);
    DbProductos oDbProductos = new DbProductos(ModuleDownloadsActivity.this);
    DbGalleryProducts oDbGalleryProducts = new DbGalleryProducts(ModuleDownloadsActivity.this);

    private String url = "https://api.massivehome.com.mx/";
    LoadingDialog oLoadingDialog = new LoadingDialog(ModuleDownloadsActivity.this,false);

    RequestQueue oQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_downloads);

        cardOptDownloadProducts = findViewById(R.id.cardOptDownloadProducts);
        cardOptDownloadCategories = findViewById(R.id.cardOptDownloadCategories);

        cardOptDownloadProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskDownloadProducts().execute();
            }
        });

        cardOptDownloadCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskDownloadCategories().execute();
            }
        });
    }
    class TaskDownloadProducts extends  AsyncTask<String, Void, String>{
        private String apiFunction ="Products";

        @Override
        protected void onPreExecute() {
            oLoadingDialog.startLoadingDialog();
            cardOptDownloadProducts.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String s) {
            oLoadingDialog.dismissDialog();
            Toast.makeText(ModuleDownloadsActivity.this,"Descarga Finalizada",Toast.LENGTH_SHORT).show();
            cardOptDownloadProducts.setEnabled(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject oJSONObject = new JSONObject();

            try {
                oJSONObject.put("Marca","false");
                oJSONObject.put("Categoria","false");
                oJSONObject.put("Gallery","true");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String requestBody = oJSONObject.toString();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oQueue = Volley.newRequestQueue(ModuleDownloadsActivity.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url + apiFunction, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for(int i = 0; i<response.length();i++)
                    {
                        try {
                            JSONObject oJson = response.getJSONObject(i);

                            String id = oJson.getString("id");
                            String codigo = oJson.getString("product_code2");
                            if(codigo.isEmpty()){
                                codigo = oJson.getString("product_code");
                            }

                            String nombre = oJson.getString("nameProduct");
                            String desc = oJson.getString("descriptionProduct");

                            String category_id = oJson.getString("categoryId");
                            String brand_id = oJson.getString("brandId");

                            //String statusId = oJson.getString("statusId");

                            String precio = oJson.getString("priceRetail");
                            String precio2 = oJson.getString("priceWoleSale");
                            String precio3 = oJson.getString("priceDistribuitor");
                            String image_name = oJson.getString("Img");

                            String filter = " WHERE id = "+id;
                            JSONArray jaGal = oJson.getJSONArray("gallery_products");


                            HashMap oHasMap = new HashMap();
                            oHasMap.put("id",id);
                            oHasMap.put("codigo",codigo);
                            oHasMap.put("nombre",nombre);
                            oHasMap.put("descripcion",desc);

                            oHasMap.put("category_id",category_id);
                            oHasMap.put("brand_id",brand_id);

                            oHasMap.put("image_name",image_name);

                            oHasMap.put("precio",precio);
                            oHasMap.put("precio2",precio2);
                            oHasMap.put("precio3",precio3);

                            if(oDbProductos.ifExist(filter))
                            {
                                if(oDbProductos.update(oHasMap))
                                {
                                    if(oDbGalleryProducts.deleteGallery(Integer.parseInt(id))){
                                        if(jaGal.length()>0){
                                            for(int g=0;g<jaGal.length();g++){
                                                JSONObject oJsonGal = jaGal.getJSONObject(g);
                                                String img = oJsonGal.getString("img");
                                                HashMap oHasMapGall = new HashMap();
                                                oHasMap.put("product_id",id);
                                                oHasMap.put("image_name",img);
                                                oDbGalleryProducts.insert(oHasMapGall);
                                                //Log.d("imagen : ",img);
                                            }
                                        }
                                    }
                                }

                            }else{

                                long newId = oDbProductos.insert(oHasMap);
                                if(newId>0)
                                {

                                    if(jaGal.length()>0)
                                    {
                                        for(int g=0;g<jaGal.length();g++)
                                        {
                                            JSONObject oJsonGal = jaGal.getJSONObject(g);
                                            String img = oJsonGal.getString("img");
                                            HashMap oHasMapGall = new HashMap();
                                            oHasMapGall.put("product_id",id);
                                            oHasMapGall.put("image_name",img);
                                            oDbGalleryProducts.insert(oHasMapGall);
                                            //Log.d("imagen : ",img);
                                        }
                                    }


                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    //params.put("Authorization", myToken);
                    return params;
                }
            };

            oQueue.add(request);
            //return strings[0];
            return null;
        }
    }
    class TaskDownloadCategories extends AsyncTask<String,Void,String> {
        private String apiFunction ="Categories/all";
        @Override
        protected void onPreExecute() {
            oLoadingDialog.startLoadingDialog();
            cardOptDownloadProducts.setEnabled(false);
            //super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            oLoadingDialog.dismissDialog();
            cardOptDownloadProducts.setEnabled(true);
            Toast.makeText(ModuleDownloadsActivity.this,"Descarga Finalizada",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oQueue = Volley.newRequestQueue(ModuleDownloadsActivity.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + apiFunction, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for(int i = 0; i<response.length();i++)
                    {
                        try {
                            JSONObject oJson = response.getJSONObject(i);
                            String nombre = oJson.getString("categoryName");
                            String id = oJson.getString("id");
                            String category_id = oJson.getString("categoryId");
                            String statusId = oJson.getString("statusId");
                            String filter = " WHERE id = "+id;

                            HashMap oHasMap = new HashMap();
                            oHasMap.put("id",id);
                            oHasMap.put("category_id",category_id);
                            oHasMap.put("nombre",nombre);
                            oHasMap.put("statusId",statusId);

                            if(oDbCategorias.ifExist(filter)){
                                oDbCategorias.update(oHasMap);
                            }else{
                                oDbCategorias.insert(oHasMap);
                            }
                            //Log.d("Categoria",name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            oQueue.add(request);
            //return strings[0];
            return null;
        }
    }
}