package com.svalero.storeapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.inventory.RegisterMapInventoryContract;
import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.domain.InventoryMapDTO;
import com.svalero.storeapp.presenter.inventory.RegisterMapInventoryPresenter;

public class SelectMapView extends AppCompatActivity implements Style.OnStyleLoaded, RegisterMapInventoryContract.View {
    private double gpsLatitude;
    private double gosLongitude;
    private boolean autoMarker = false;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private Point point;
    private FusedLocationProviderClient fusedLocationClient;
    private String username;
    private String token;
    private Inventory inventory;
    private RegisterMapInventoryPresenter registerMapInventoryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //gps();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map_view);

        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        token = intent.getStringExtra("token");
        inventory = (Inventory) intent.getSerializableExtra("inventory");


        GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(mapView);
        gesturesPlugin.addOnMapClickListener(point -> {
            removeAllMarkers();
            this.point = point;
            addMarker(point);
            return true;
        });

        initializePointAnnotationManager();
        registerMapInventoryPresenter = new RegisterMapInventoryPresenter(this);
    }

    private void initializePointAnnotationManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {

    }

    public void saveMap(View view){
        if(point == null) {
            Toast.makeText(this, R.string.mapMessage, Toast.LENGTH_LONG).show();

        } else {
            InventoryMapDTO inventoryMapDTO = new InventoryMapDTO(point.latitude(), point.longitude());
            registerMapInventoryPresenter.registerMap(token, inventory.getId(), inventoryMapDTO);
        }
    }

    private void addMarker(Point point) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker_foreground));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void addMarker(double latitude, double longitude, int id) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(BitmapFactory.decodeResource(getResources(), id));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void removeAllMarkers() {
        pointAnnotationManager.deleteAll();
        point = null;
    }

    public void back(View view){
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }

    public void removeMarkers(View view){
        removeAllMarkers();
    }


    /*
    @SuppressLint("MissingPermission")
    private void gps() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                // Logic to handle location object
                if(preference.isAutoPlaceMarker()){
                    gosLongitude = location.getLongitude();
                    gpsLatitude = location.getLatitude();
                    addMarker(location.getLatitude(), location.getLongitude(), R.mipmap.blue_marker_icons_foreground);
                    autoMarker = true;
                }

                setCameraPosition(location.getLatitude(), location.getLongitude());
            }
        });
    }

     */

    private void setCameraPosition(double latitude, double longitude) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(Point.fromLngLat(longitude, latitude))
                .pitch(45.0)
                .zoom(15.5)
                .bearing(-17.6)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showInventory(Inventory inventory) {
        Toast.makeText(this, "Coordenadas: " + inventory.getLatitude() + "," + inventory.getLongitude() + " añadidas!", Toast.LENGTH_LONG).show();
    }
}