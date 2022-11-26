package com.example.themoviemvvm.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.themoviemvvm.R
import com.example.themoviemvvm.databinding.FragmentDetailBinding
import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.Video
import com.example.themoviemvvm.ui.viewmodel.DetailViewModel
import com.example.themoviemvvm.ui.viewmodel.statesviewmodel.MovieScreenState
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private val viewmodel by viewModels<DetailViewModel>()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var urlTriller = ""
    private var urlTeaser = ""
    private var scrollRange = -1
    private var isShow = false
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var _movie : DetailMovie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        initCollapsingToolbar()
        lifecycleScope.launchWhenResumed {
            subscribeToMovieState()
        }

        binding.txHomePage.setOnClickListener(this)
        binding.bnTeaser.setOnClickListener(this)
        binding.bnTrailler.setOnClickListener(this)
        viewmodel.fetchDetailMovie(args.idMovie)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout?.totalScrollRange ?: -1
        }
        if (scrollRange + verticalOffset == 0) {
            binding.collapsingToolbar.title = getString(R.string.app_name)
            isShow = true
        } else {
            binding.collapsingToolbar.title = ""
            isShow = false
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bnTrailler -> {
                openIntenActionView(urlTriller)
            }
            R.id.bnTeaser -> {
                openIntenActionView(urlTeaser)
            }
            R.id.txHomePage -> {
                openIntenActionView(binding.txHomePage.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeToMovieState() {
        viewmodel.state.onEach(::handleMovieListState).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleMovieListState(screenState: MovieScreenState) {
        when (screenState) {
            is MovieScreenState.Success -> {
                hideProgressBar()
                setMovie(screenState.movie)
            }
            is MovieScreenState.Failure -> {
                hideProgressBar()
                showError(screenState.error)
            }
            is MovieScreenState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is MovieScreenState.SuccessVideo -> {
                hideProgressBar()
                setVideos(screenState.video)
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }


    private fun setMovie(movie: DetailMovie) {
        with(binding) {
            val urlImage = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            Glide.with(requireContext()).load(urlImage).placeholder(R.drawable.load)
                .into(thumbnailImageheader)
            txTitle.text = movie.title
            txHomePage.text = movie.homepage
            txBudget.text = movie.budget.toString()
            txOverview.text = movie.overview
            txPopularity.text = movie.popularity.toString()
            txReleaseDate.text = movie.releaseDate
            txOriginalTitle.text = movie.originalTitle
            txLanguage.text = movie.originalLanguage
            _movie = movie
            viewmodel.fetchVideoMovie(args.idMovie)
        }
    }

    private fun setVideos(video: List<Video>) {
        if (video.isNotEmpty()){
            for (item in video){
                if (item.site == "YouTube"){
                    when(item.type){
                        "Trailer" -> {
                            binding.bnTrailler.visibility = View.VISIBLE
                            urlTriller = "https://www.youtube.com/watch?v=${item.key}"
                        }
                        "Teaser" -> {
                            binding.bnTeaser.visibility = View.VISIBLE
                            urlTeaser = "https://www.youtube.com/watch?v=${item.key}"
                        }
                        else -> {
                            binding.bnTrailler.visibility = View.GONE
                            binding.bnTeaser.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun initCollapsingToolbar() {
        binding.collapsingToolbar.title = ""
        binding.appBar.setExpanded(true)
        binding.appBar.addOnOffsetChangedListener(this)
    }

    private fun openIntenActionView(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}