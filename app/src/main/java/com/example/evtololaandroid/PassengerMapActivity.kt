package com.example.evtololaandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.azure.android.maps.control.*
import com.azure.android.maps.control.events.OnFeatureClick
import com.azure.android.maps.control.events.OnReady
import com.azure.android.maps.control.layer.BubbleLayer
import com.azure.android.maps.control.options.AnchorType
import com.azure.android.maps.control.options.CameraOptions
import com.azure.android.maps.control.options.PopupOptions
import com.azure.android.maps.control.source.DataSource
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point


class PassengerMapActivity : AppCompatActivity() {
    companion object {
        init {
            AzureMaps.setSubscriptionKey("");
        }
    }

    var mapControl: MapControl? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.passenger_map)
        mapControl = findViewById(R.id.mapcontrol)

        mapControl?.onCreate(savedInstanceState)

        //Wait until the map resources are ready.
        mapControl?.onReady(OnReady { map: AzureMap ->
            map.setCamera(

                CameraOptions.center(Point.fromLngLat(-122.497516, 47.174625)),

                //The zoom level. Typically a value between 0 and 22.
                CameraOptions.zoom(11.0),

                //The amount of tilt in degrees the map where 0 is looking straight down.
                CameraOptions.pitch(45.0),

                //Direction the top of the map is pointing in degrees. 0 = North, 90 = East, 180 = South, 270 = West
                CameraOptions.bearing(90.0),
            )

            val source = DataSource()
            source.importDataFromUrl("asset://PilotsInfo.json")
            map.sources.add(source)
            val layer = BubbleLayer(source)
            map.layers.add(layer)
            val popup = Popup()
            map.popups.add(popup)

            map.events.add(OnFeatureClick { feature: List<Feature> ->
                //Get the first feature and it's properties.
                val f = feature[0]

                //Retrieve the custom layout for the popup.
                val customView: View = LayoutInflater.from(this).inflate(R.layout.popup_text, null)
                //Display the name and entity type information of the feature into the text view of the popup layout.
                val tv = customView.findViewById<TextView>(com.azure.android.maps.control.R.id.message)
                val pos = MapMath.getPosition(f.geometry() as Point?)
                tv.text = String.format(
                    "%s\n%s\n%s",
                    f.getStringProperty("Name"),
                    f.getStringProperty("EntityType"),
                    pos
                )
                //Get the position of the clicked feature.


                //Set the options on the popup.
                popup.setOptions( //Set the popups position.
                    PopupOptions.position(pos),  //Set the anchor point of the popup content.
                    PopupOptions.anchor(AnchorType.BOTTOM),  //Set the content of the popup.
                    PopupOptions.content(customView)
                )
                //Open the popup.
                popup.open()
                //Return a boolean indicating if event should be consumed or continue to bubble up.
                false
            } as OnFeatureClick, layer)

        })
    }



    public override fun onStart() {
        super.onStart()
        mapControl?.onStart()
    }

    public override fun onResume() {
        super.onResume()
        mapControl?.onResume()
    }

    public override fun onPause() {
        mapControl?.onPause()
        super.onPause()
    }

    public override fun onStop() {
        mapControl?.onStop()
        super.onStop()
    }

    override fun onLowMemory() {
        mapControl?.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroy() {
        mapControl?.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapControl?.onSaveInstanceState(outState)
    }
}