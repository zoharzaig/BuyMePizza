import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buymepizza.Contact
import com.example.buymepizza.R
import com.example.buymepizza.fragment_contact_list

class ContactAdapter (val contactList:List<Contact>, val view : fragment_contact_list) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val number: TextView

        init {
            name = view.findViewById(R.id.tv_name)
            number = view.findViewById(R.id.tv_number)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ContactViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.contact_item, viewGroup, false)

        return ContactViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ContactViewHolder, position: Int) {

        viewHolder.name.text = contactList[position].name
        viewHolder.number.text = contactList[position].number

        val itemsViewModel = contactList[position]
        viewHolder.name.setOnClickListener(){ view.onContactClicked(
            itemsViewModel
        ) }

        viewHolder.number.setOnClickListener(){ view.onContactClicked(
            itemsViewModel
        ) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = contactList.size

}
