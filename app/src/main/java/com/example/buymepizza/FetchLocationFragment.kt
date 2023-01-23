package com.example.buymepizza

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.buymepizza.databinding.FragmentFetchLocationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FetchLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FetchLocationFragment : Fragment() {

    private var _binding : FragmentFetchLocationBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFetchLocationBinding.inflate(inflater, container, false)

        val permissionlauncher : ActivityResultLauncher<String> =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
                ActivityResultCallback {
                    if(it){
                        Toast.makeText(requireActivity(), R.string.location_enabled, Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_fetchLocationFragment_to_shareLocationFragment)
                        }
                    else
                        Toast.makeText(requireActivity(), R.string.location_denied, Toast.LENGTH_LONG).show()
                })

        binding.GetLocationButton.setOnClickListener{

            if (ContextCompat.checkSelfPermission(
                    requireActivity().application, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissionlauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
            else
            {
                findNavController().navigate(R.id.action_fetchLocationFragment_to_shareLocationFragment)

            }
        }
        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}