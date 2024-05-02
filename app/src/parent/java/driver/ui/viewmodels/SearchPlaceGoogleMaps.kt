package driver.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.tripManagement.ParentTripManager
import javax.inject.Inject

@HiltViewModel
class SearchPlaceGoogleMapsViewModel @Inject constructor(private val pla:ParentTripManager, @ApplicationContext applicationContext: Context): ViewModel() {


    init {
        Places.initialize(applicationContext, "AIzaSyANMz3n_soyBll2XNWR8inxnDeFb2ipdAc")
    }

    val field = listOf(Place.Field.NAME, Place.Field.LAT_LNG)

}