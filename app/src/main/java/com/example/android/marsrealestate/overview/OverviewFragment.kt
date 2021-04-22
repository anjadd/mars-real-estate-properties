package com.example.android.marsrealestate.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.network.MarsApiFilter

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize [OverviewViewModel], which means the OverviewViewModel is created the
     * first time it is used.
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater)

        /* Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
         * Because you've set the lifecycle owner, any LiveData used in data binding will
         * automatically be observed for any changes, and the UI will be updated accordingly.*/
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Set the adapter of the RecyclerView
        binding.rvMarsProperties.adapter = PhotoGridAdapter(MarsPropertyListener { marsProperty ->
            viewModel.displayMarsPropertyDetails(marsProperty)
        })

        /** Implement navigation from the overview to the detail fragment */
        viewModel.navigateToMarsPropertyDetails.observe(viewLifecycleOwner) { marsProperty ->
            marsProperty?.let {
                //Toast.makeText(context, "Property for rent: ${marsProperty.isRental}", Toast.LENGTH_SHORT).show()
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(marsProperty))
                viewModel.onMarsPropertyDetailsNavigationDone()
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Filter the Mars properties by type (rent/buy/all)
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_rent_menu -> viewModel.updateFilter(MarsApiFilter.SHOW_RENT)
            R.id.show_buy_menu -> viewModel.updateFilter(MarsApiFilter.SHOW_BUY)
            else -> viewModel.updateFilter(MarsApiFilter.SHOW_ALL)
        }
        return true
    }
}
