package com.svalero.storeapp.view;

import static com.mapbox.core.constants.Constants.PRECISION_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.GeoJson;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.LayerUtils;
import com.mapbox.maps.extension.style.layers.generated.LineLayer;
import com.mapbox.maps.extension.style.sources.SourceUtils;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.storeapp.R;
import com.svalero.storeapp.domain.Inventory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeMapView extends AppCompatActivity implements Style.OnStyleLoaded, Callback<DirectionsResponse> {

    private double gpsLatitude;
    private double gosLongitude;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private String username;
    private Inventory inventory;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_map_view);

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        inventory = (Inventory) intentFrom.getSerializableExtra("inventory");

        mapView = findViewById(R.id.mapViewDetail);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);
        initializePointAnnotationManager();
    }

    private void initializePointAnnotationManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    public void onStyleLoaded(@NonNull Style style) {
        addMarker(inventory.getLatitude(), inventory.getLongitude(), String.valueOf(inventory.getId()), R.mipmap.red_marker_foreground);
        setCameraPosition(inventory.getLatitude(), inventory.getLongitude());
        gps();


    }

    public void getRoute(View view){
        Point origin = Point.fromLngLat(inventory.getLongitude(), inventory.getLatitude());
        Point destination = Point.fromLngLat(gosLongitude, gpsLatitude);
        calculateRoute(origin, destination);
    }

    private void addMarker(double latitude, double longitude, String title, int id) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(BitmapFactory.decodeResource(getResources(), id))
                .withTextField(title);
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void setCameraPosition(double latitude, double longitude) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(Point.fromLngLat(longitude, latitude))
                .pitch(45.0)
                .zoom(15.5)
                .bearing(-17.6)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);
    }

    @SuppressLint("MissingPermission")
    private void gps() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        gosLongitude = location.getLongitude();
                        gpsLatitude = location.getLatitude();
                        Log.i("gps: ", "+++++++++++");
                        Log.i("gps: ", String.valueOf(location.getLongitude()));
                        Log.i("gps: ", String.valueOf(location.getLatitude()));
                        Log.i("gps: ", String.valueOf(location));

                        /*
                        if(preference.isMapDetailCenterMe()){
                            setCameraPosition(gpsLatitude, gosLongitude);
                        }

                         */

                        addMarker(gpsLatitude, gosLongitude, "Yo", R.mipmap.red_marker_foreground);

                    }
                });

    }

    private void calculateRoute(Point origin, Point destination) {
        RouteOptions routeOptions = RouteOptions.builder()
                .baseUrl(Constants.BASE_API_URL)
                .user(Constants.MAPBOX_USER)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .steps(true)
                .coordinatesList(List.of(origin, destination))
                .build();
        MapboxDirections directions = MapboxDirections.builder()
                .routeOptions(routeOptions)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();
        directions.enqueueCall(this);
    }

    private void back(View view){
        Intent intent = new Intent(this, InventoryListView.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        DirectionsRoute mainRoute = response.body().routes().get(0);
        mapView.getMapboxMap().getStyle(style -> {
            LineString routeLine = LineString.fromPolyline(mainRoute.geometry(), PRECISION_6);

            GeoJsonSource routeSource = new GeoJsonSource.Builder("trace-source")
                    .geometry(routeLine)
                    .build();
            LineLayer routeLayer = new LineLayer("trace-leyer", "trace-source")
                    .lineWidth(7.f)
                    .lineColor(Color.BLUE)
                    .lineOpacity(1f);
            SourceUtils.addSource(style, routeSource);
            LayerUtils.addLayer(style, routeLayer);
        });
    }

    @Override
    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

    }
}