package com.example.savemylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savemylife.databinding.MainActivityBinding
import com.example.savemylife.ui.main.SosActionModel
import com.example.savemylife.ui.main.MainActivityAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.sosActionRecyclerView
        val sosActions = arrayListOf<SosActionModel>()
        val iconIds = resources.obtainTypedArray(R.array.sos_action_icon_ids)
        val titles = resources.getStringArray(R.array.sos_action_titles)
        val settingsButtonOnClickListeners: List<View.OnClickListener> = GetSettingsButtonClickListeners()
        for (i in 0 until iconIds.length()){
            val sosAction = SosActionModel(iconIds.getResourceId(i, 0), titles[i], false, settingsButtonOnClickListeners[i])
            sosActions.add(sosAction)
        }
        iconIds.recycle()
        recyclerView.adapter = MainActivityAdapter(this, sosActions)

    }

    private fun GetSettingsButtonClickListeners(): List<View.OnClickListener> {
        val listeners = mutableListOf<View.OnClickListener>()
        val settingsButtonActivityClasses = listOf(SosSmsActionActivity::class.java, SosSmsActionActivity::class.java, SosSmsActionActivity::class.java, SosSmsActionActivity::class.java)
        for (settingsButtonActivityClass in settingsButtonActivityClasses){
            listeners.add { view: View? ->
                val intent = Intent(this, settingsButtonActivityClass)
                this.startActivity(intent)
            }
        }
        return listeners
    }
}