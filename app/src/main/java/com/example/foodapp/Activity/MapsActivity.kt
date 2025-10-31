package com.example.foodapp.Activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //Fused Location
    private lateinit var fusedlocation: FusedLocationProviderClient

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // Fused Location GPS
        fusedlocation = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Create Markers
        //-1.2488143817335098, 36.785455599396585
        val westlands = LatLng(-1.2488143817335098, 36.785455599396585)
        mMap.addMarker(MarkerOptions().position(westlands).title("Westlands")
            .snippet("Villa Rosa Kempinsky").icon(
                BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.shoppingcart)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(westlands, 15f))//zoom the camera
        //-1.2112048989665076,36.78828944590323
        val nairobi = LatLng(-1.2112048989665076,36.78828944590323)
        mMap.addMarker(MarkerOptions().position(nairobi).title("Marker in Nairobi")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nairobi, 15f))//zoom the camera
        //
        val RidgewayMall = LatLng(-1.2216738822942779, 36.839272875934306)
        mMap.addMarker(MarkerOptions().position(RidgewayMall).title("Marker in Ridgeways")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.shoppingcart)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(RidgewayMall))
        //
        val JKIA = LatLng(-1.3153780115762275, 36.92570438012945)
        mMap.addMarker(MarkerOptions().position(JKIA).title("Marker in Fedha")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(JKIA))
        //
        val Jain_Shwetamber = LatLng(-1.2642358855553015, 36.81755771150572)
        mMap.addMarker(MarkerOptions().position(Jain_Shwetamber).title("Marker in Jain_Shwetamber")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Jain_Shwetamber))
        //-1.228538767637185, 36.87841167049442
        val GardenCity = LatLng(-1.228538767637185, 36.87841167049442)
        mMap.addMarker(MarkerOptions().position(GardenCity).title("Marker in GardenCity")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.shoppingcart)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(GardenCity))


        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // SetUp GPS Function
        fun setupGPS(){
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
                return
            }//End If
            mMap.isMyLocationEnabled=true //Users need to activate GPS on their devices
            fusedlocation.lastLocation.addOnSuccessListener(this) {location ->
                if (location != null){
                    val currentLocation = LatLng(location.latitude,location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15f))
                }// End of if
            }
        }
        setupGPS()
    }
}