package com.lanang.android.BelajarUAS

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lanang.android.BelajarUAS.databinding.FragmentProfileBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding
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
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
        val db = Firebase.firestore

        // Query untuk mengambil dokumen terbaru berdasarkan field 'time'
        db.collection("usersScore")
            .orderBy("time", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1) // Hanya ambil satu dokumen terbaru
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0] // Ambil dokumen pertama
                    val total = document.getString("score") // Dapatkan nilai 'score'
                    binding.dailyScore.text = "$total" // Tampilkan dalam TextView
                } else {
                    Log.d(TAG, "No documents found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Query failed with: ", exception)
            }

        // Query untuk data mingguan
        db.collection("usersScore")
            .orderBy("time", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(7) // Ambil maksimum 7 dokumen terbaru
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Ambil skor dari dokumen-dokumen
                    val scores = documents.documents.mapNotNull {
                        it.getString("score")?.toFloatOrNull()
                    }

                    if (scores.isNotEmpty()) {
                        // Hitung rata-rata
                        val averageScore = scores.average()
                        // Format hasil rata-rata
                        val formattedAverage = String.format("%.1f", averageScore)
                        // Tampilkan rata-rata mingguan di TextView
                        binding.weekly.text = "$formattedAverage/4.0"
                    } else {
                        Log.d(TAG, "No valid scores found in documents")
                        binding.weekly.text = "No data available"
                    }
                } else {
                    Log.d(TAG, "No weekly documents found")
                    binding.weekly.text = "No data available"
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Query failed with: ", exception)
                binding.weekly.text = "Error fetching data"
            }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}