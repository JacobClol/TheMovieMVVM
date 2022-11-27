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
import com.example.themoviemvvm.R
import com.example.themoviemvvm.core.utils.NetworkState
import com.example.themoviemvvm.databinding.FragmentMainBinding
import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.ui.adapter.MoviesAdapter
import com.example.themoviemvvm.ui.viewmodel.MainViewModel
import com.example.themoviemvvm.ui.viewmodel.statesviewmodel.MovieListScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainFragment : Fragment(), MoviesAdapter.OnMovieClickListener {

    private val viewmodel by viewModels<MainViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val listFavoriteMovie: ArrayList<DetailMovie> = ArrayList()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var isSelectListFavorite = false
    private val adapter by lazy {
        MoviesAdapter(requireContext(), this)
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

        setUpRecyclerView()
        setupSwipeRefreshLayout()
        initToolbar()
        lifecycleScope.launchWhenResumed {
            subscribeToMovieState()
        }
        if (NetworkState.isNetworkConnected && !isSelectListFavorite){
            viewmodel.fetchPopularMovies()
        } else {
            viewmodel.fetchFavoriteMovies()
        }
    }

    override fun onMovieClick(idMovie: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(idMovie)
        findNavController().navigate(action)
    }

    override fun onFavoriteClick(position: Int){
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
            listFavoriteMovie[position].id,
            listFavoriteMovie[position]
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.rvListMovies.layoutManager = GridLayoutManager(context, 2)
        } else {
            binding.rvListMovies.layoutManager = GridLayoutManager(context, 4)
        }
        binding.rvListMovies.itemAnimator = DefaultItemAnimator()
        binding.rvListMovies.adapter = adapter
    }

    private fun subscribeToMovieState() {
        viewmodel.state.onEach(::handleMovieListState).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleMovieListState(screenState: MovieListScreenState) {
        when (screenState) {
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
            is MovieListScreenState.SuccessFavorite -> {
                hideProgressBar()
                hideRefreshing()
                showFavoriteMovies(screenState.data)
            }
        }
    }

    private fun showMovies(data: List<Movie>) {
        isSelectListFavorite = false
        adapter.submitList(data)
    }

    private fun showFavoriteMovies(list: List<DetailMovie>){
        isSelectListFavorite = true
        listFavoriteMovie.addAll(list)
        val listMovie = list.map { it.toMovie() }
        adapter.submitListFavorite(listMovie)
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

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun hideRefreshing() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun initToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        with(binding) {
            toolbar.setupWithNavController(navController, appBarConfiguration)
            toolbar.inflateMenu(R.menu.main)
            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_favorite ->{
                        viewmodel.fetchFavoriteMovies()
                        true
                    }
                    R.id.action_top_rate -> {
                        viewmodel.fetchTopRateMovies()
                        true
                    }
                    R.id.action_popularity -> {
                        adapter.orderByPopularity()
                        true
                    }
                    R.id.action_rate -> {
                        adapter.orderByRate()
                        true
                    }
                    R.id.action_votes -> {
                        adapter.orderByVote()
                        true
                    }
                    else -> false
                }
            }
        }

    }
}