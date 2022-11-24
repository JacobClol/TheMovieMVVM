package com.example.themoviemvvm.ui.fragment

import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.themoviemvvm.ui.adapter.MoviesAdapter
import com.example.themoviemvvm.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MoviesAdapter.OnMovieClickListener, CompoundButton.OnCheckedChangeListener {

    private val viewmodel: MainViewModel by viewModels()

    override fun onMovieClick(idMovie: Int) {
        TODO("Not yet implemented")
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        TODO("Not yet implemented")
    }
}