package com.example.themoviemvvm.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.themoviemvvm.databinding.FragmentMainBinding
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.ui.adapter.MoviesAdapter
import com.example.themoviemvvm.ui.viewmodel.MainViewModel
import com.example.themoviemvvm.ui.viewmodel.MovieListScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainFragment : Fragment(), MoviesAdapter.OnMovieClickListener {

    private val viewmodel by viewModels<MainViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val listMovie: ArrayList<Movie> = ArrayList()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val adapter by lazy {
        MoviesAdapter(requireContext(), listMovie, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        setUpRecyclerView()
        setupSwipeRefreshLayout()
        lifecycleScope.launchWhenResumed {
            subscribeToMovieState()
        }
        viewmodel.fechtPopularMovies()
    }

    override fun onMovieClick(idMovie: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(idMovie)
        findNavController().navigate(action)
    }

    private fun setUpRecyclerView(){
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            binding.rvListMovies.layoutManager = GridLayoutManager(context, 2)
        } else {
            binding.rvListMovies.layoutManager = GridLayoutManager(context, 4)
        }
        binding.rvListMovies.itemAnimator = DefaultItemAnimator()
        binding.rvListMovies.adapter = adapter
    }

    private fun subscribeToMovieState(){
        viewmodel.state.onEach(::handleMovieListState).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleMovieListState(screenState: MovieListScreenState){
        when(screenState){
            is MovieListScreenState.Success -> {
                hideProgressBar()
                hideRefreshing()
                showMovies(screenState.data)
            }
            is MovieListScreenState.Failure -> {
                hideProgressBar()
                hideRefreshing()
                showError(screenState.error)
            }
            is MovieListScreenState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun showMovies(data: List<Movie>) {
        adapter.submitList(data)
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewmodel.refresh()
        }
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun hideRefreshing(){
        binding.swipeRefreshLayout.isRefreshing = false
    }
}