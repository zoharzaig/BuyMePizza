package com.example.buymepizza

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.buymepizza.databinding.FragmentShareLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShareLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ShareLocationFragment : Fragment() {

    private var _binding: FragmentShareLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var addressString : String

    private val addressViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity().application)
        locateMe()

        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShareLocationBinding.inflate(inflater, container, false)

        val callPermissionLauncher : ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission(), ActivityResultCallback{
                if(it) {
                    Toast.makeText(context,R.string.contacts_enabled, Toast.LENGTH_SHORT).show()
                    //move to next fragment if permission is granted
                    findNavController().navigate(R.id.action_shareLocationFragment_to_fragment_contact_list2)
                }
                else {
                    Toast.makeText(context,R.string.contacts_denied, Toast.LENGTH_SHORT).show()
                    //open the app settings for the user if permission is not granted
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().application.packageName , null)
                    intent.data = uri
                    startActivity(intent)
                }
            })

        binding.ShareLocationButton.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED )
            {
                callPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
            }

            else {
                findNavController().navigate(R.id.action_shareLocationFragment_to_fragment_contact_list2)
            }
        }
        return binding.root
    }


    fun locateMe() {

        var geocoder = Geocoder(requireActivity(), Locale.getDefault())
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener { location: Location? ->
            if (location == null) {
                Toast.makeText(
                    requireActivity(),
                    R.string.location_service_off,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                var latitude = location.latitude
                var longtitude = location.longitude
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                binding.addressTV.text = "Your address: \n "  + address[0].getAddressLine(0)
                addressString = address[0].getAddressLine(0)
                addressViewModel.setAddress(addressString)
            }

        }
 }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
