package com.example.roomapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mynote.database.NoteDao
import com.example.mynote.database.NoteRoomDatabase
import com.example.roomapplication.databinding.ActivityReadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadBinding
    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService
    private var noteId: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!
        noteId = intent.getIntExtra("note_id", 0)

        loadPemilihData()
        with(binding){
            btnKembaliLihat.setOnClickListener(){
                startActivity(Intent(this@ReadActivity,MainActivity::class.java))
            }
        }

    }


    private fun loadPemilihData() {
        lifecycleScope.launch (Dispatchers.IO) {
            mNotesDao.getVoterById(noteId).collect { pemilih ->
                if (pemilih != null) {
                    withContext(Dispatchers.Main) {
                        binding.etNamaLihat.setText(pemilih.nama_pemilih)
                        binding.etNikLihat.setText(pemilih.nik)
                        binding.etGenderLihat.setText(pemilih.gender)
                        binding.etAlamatLihat.setText(pemilih.alamat)
                    }
                }
            }
        }
    }
}