package com.amindset.ve.amindset.ShowLocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Selection.SelectionActivity
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.databinding.ActivityShowLocationBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class ShowLocation : BaseActivity(), OnMapReadyCallback,LocationListener,
    GoogleApiClient.OnConnectionFailedListener,


    GoogleApiClient.ConnectionCallbacks {
    override fun onLocationChanged(location: Location?) {

    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {

        try {

            if (mGoogleApiClient == null) {
                showMessage("Hello")
            } else {
                mLocationRequest = LocationRequest()
                mLocationRequest.interval = 1000
                mLocationRequest.fastestInterval = 1000
                mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

                    mLocationCallback = LocationCallback()

                    mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())


                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient
                    );


                }


                if (mLastLocation != null) {
                    var lat = mLastLocation.getLatitude();
                    var lng = mLastLocation.getLongitude();


                    var loc: LatLng = LatLng(lat, lng);
                    mMap!!.addMarker(MarkerOptions().position(loc).title("New Marker"));
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 12.3F), 2000, null);

                    var address: StringBuffer = CommonUtils.currentAddress(this, lat, lng)

                    showLocationBinding.etLocation.setText(
                        CommonUtils.currentAddress(this, lat, lng)
                    )
                }
            }
            } catch (e : Exception)
            {
                e.printStackTrace()
            }


    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }




    lateinit var showLocationBinding: ActivityShowLocationBinding

    private var mMap: GoogleMap? = null
    internal lateinit var mLastLocation: Location
    internal lateinit var mLocationResult: LocationRequest
    internal lateinit var mLocationCallback: LocationCallback
    internal lateinit var mLocationlistener: LocationListener
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest
    internal var mFusedLocationClient: FusedLocationProviderClient? = null

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap;


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                buildGoogleApiClient()


                mMap!!.isMyLocationEnabled = true
            }

            else{
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }

//        buildGoogleApiClient()
//        mMap!!.isMyLocationEnabled = true

//        mMap!!.getUiSettings().setZoomControlsEnabled(true)
////                mMap!!.setOnMarkerClickListener(this)
//
//        val latlng :  LatLng  = LatLng(27.1767, 78.0081)
//
//        mMap!!.addMarker( MarkerOptions().position(latlng).title("sattu chacha"))
//
//        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12.3F), 2000, null);


    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_show_location)

        showLocationBinding = DataBindingUtil.setContentView(this,R.layout.activity_show_location)

       var mapFragment : SupportMapFragment = getSupportFragmentManager()
         .findFragmentById(R.id.mapView) as SupportMapFragment
          mapFragment.getMapAsync(this);

    }

    fun onNextClick(view : View)
    {
        val intent = Intent(this,SelectionActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        buildGoogleApiClient()


                        mMap!!.isMyLocationEnabled = true
                    }

                    else{
                        ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1)
                    }

                  }
                }
            }
        }
}
