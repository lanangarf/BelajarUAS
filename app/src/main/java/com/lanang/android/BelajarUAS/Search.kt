package com.lanang.android.BelajarUAS

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.lanang.android.BelajarUAS.databinding.ActivityMainBinding
import com.lanang.android.BelajarUAS.databinding.FragmentSearchBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Search : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSearchBinding
    private lateinit var resourcesLV: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    lateinit var resourcesList: ArrayList<String>;
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(myview: View, savedInstanceState: Bundle?) {
        super.onViewCreated(myview, savedInstanceState)

        val editText = binding.idR
        editText.requestFocus()

        resourcesLV = binding.Resources
        searchView = binding.idR
        resourcesList = ArrayList()
        resourcesList.add(getString(R.string.student_health_services))
        resourcesList.add(getString(R.string.financial_aid_office))
        resourcesList.add(getString(R.string.student_affairs))
        resourcesList.add(getString(R.string.career_center))
        resourcesList.add(getString(R.string.mental_stability))
        resourcesList.add(getString(R.string.time_management))
        resourcesList.add(getString(R.string.managing_stress))
        resourcesList.add(getString(R.string.friendship_guides))

        listAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resourcesList
        )

        resourcesLV.adapter = listAdapter

        resourcesLV.setOnItemClickListener { adapterView, _, position, _ ->
            when (val selectedItem = adapterView.getItemAtPosition(position) as String) {
                "Telkom Medika" -> {
                    (activity as? MainActivity)?.apply {
                        output = "081111500115"
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$output")
                        startActivity(intent)
                    }
                }
                "Bantuan Finansial" -> {
                    (activity as? MainActivity)?.apply {
                        output = "082319949941"
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$output")
                        startActivity(intent)
                    }
                }
                "Layanan Aduan DITMAWA" -> {
                    (activity as? MainActivity)?.apply {
                        output = "082130155601"
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$output")
                        startActivity(intent)
                    }
                }
                "TelU Career" -> {
                    (activity as? MainActivity)?.apply {
                        output = "082118362845"
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$output")
                        startActivity(intent)
                    }
                }
                "Kesehatan Mental" -> {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse("https://telkomuniversity.ac.id/menjaga-mental-health-sama-pentingnya-dengan-menjaga-kesehatan-fisik-apa-benar/")
                    startActivity(openURL)
                }
                "Manajemen Waktu" -> {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse("https://seb.telkomuniversity.ac.id/manajemen-waktu/")
                    startActivity(openURL)
                }
                "Manajemen Stress" -> {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse("https://telkomuniversity.ac.id/menjaga-kesehatan-mental-dengan-membaca-buku/")
                    startActivity(openURL)
                }
                "Panduan Sosial" -> {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse("https://telkomuniversity.ac.id/temukan-rumah-kedua-melalui-komunitas-daerah-di-telkom-university/")
                    startActivity(openURL)
                }
                else -> {
                    Toast.makeText(requireContext(), "Clicked $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (resourcesList.contains(query)) {

                    listAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.filter.filter(newText)
                return false
            }

        })

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Search().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}