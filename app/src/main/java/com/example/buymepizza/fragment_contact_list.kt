package com.example.buymepizza

import ContactAdapter
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buymepizza.databinding.FragmentContactListBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_contact_list.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_contact_list : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val addressViewModel by activityViewModels<MainViewModel>()

    val cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    var contactList: ArrayList<Contact> = ArrayList()

    private var _binding: FragmentContactListBinding? = null
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
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        binding.contactList.layoutManager = LinearLayoutManager(requireActivity())
        readContacts()
       binding.addressTV.setText("Your address: " + addressViewModel.out.value)
        return binding.root
    }

    @SuppressLint("Range")
    private fun readContacts() {
        contactList.clear();
        var tvNumber: String
        var tvName: String

        val rs: Cursor = activity?.getContentResolver()?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )!!

        if (rs.getCount() > 0) {

            while (rs.moveToNext()) {
                val hasPhoneNumber: Int =
                    rs.getString(rs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER))
                        .toInt()
                if (hasPhoneNumber > 0) {
                    tvName =
                        rs.getString(rs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    tvNumber =
                        rs.getString(rs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    if (rs!!.moveToNext()) {
                        contactList.add(Contact(tvName, tvNumber))
                    }
                }
            }
        }

        rs.close()

        val adapter = ContactAdapter(contactList, this)
        binding.contactList.adapter = adapter

    }

    fun onContactClicked(contact: Contact) {

        val url =
            "https://api.whatsapp.com/send?phone=${contact.number}&text=Here is my address, will you buy me a pizza?%0A%0A${addressViewModel.out.value}"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            this.data = Uri.parse(url)
            this.`package` = "com.whatsapp"
        }

        try {
            context?.startActivity(intent)
        } catch (ex : ActivityNotFoundException){
            //whatsapp not installled on device
        }
    }
    }
