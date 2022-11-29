package com.example.themoviemvvm.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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

    private var urlTrailer = ""
    private var urlTeaser = ""
    private var scrollRange = -1
    private var isShow = false
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var _movie: DetailMovie
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCollapsingToolbar()

        lifecycleScope.launchWhenResumed {
            subscribeToMovieState()
        }

        binding.txHomePage.setOnClickListener(this)
        binding.bnTeaser.setOnClickListener(this)
        binding.bnTrailler.setOnClickListener(this)

        viewmodel.initDetailMovie(args.idMovie, args.detailMovie)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout?.totalScrollRange ?: -1
        }
        if (scrollRange + verticalOffset == 0) {
            binding.collapsingToolbar.title = _movie.originalTitle
            isShow = true
        } else {
            binding.collapsingToolbar.title = ""
            isShow = false
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bnTrailler -> {
                openIntenActionView(urlTrailer)
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
            is MovieScreenState.SaveMovie -> {
                hideProgressBar()
                showMessage(screenState.msg)
            }
            is MovieScreenState.SetVideoLocal -> {
                showBtnVideos()
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun showMessage(error: String) {
        isFavorite = !isFavorite
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun setMovie(movie: DetailMovie) {
        with(binding) {
            isFavorite = movie.isFavorite
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
            setInitIconMovieSave(binding.toolbar.menu.findItem(R.id.action_favorite))
            viewmodel.fetchVideoMovie(movie.id, isFavorite)
        }
    }

    private fun setVideos(video: List<Video>) {
        if (video.isNotEmpty()) {
            for (item in video) {
                if (item.site == "YouTube") {
                    when (item.type) {
                        "Trailer" -> {
                            _movie.trailer = "https://www.youtube.com/watch?v=${item.key}"
                        }
                        "Teaser" -> {
                            _movie.teaser = "https://www.youtube.com/watch?v=${item.key}"
                        }
                        else -> {
                           hideTeaser()
                        }
                    }
                    showBtnVideos()
                }
            }
        }
    }

    private fun showBtnVideos(){
        _movie.trailer?.let {
            binding.bnTrailler.visibility = View.VISIBLE
        }
        _movie.teaser?.let {
            binding.bnTeaser.visibility = View.VISIBLE
        }
    }

    private fun hideTeaser(){
        binding.bnTrailler.visibility = View.GONE
        binding.bnTeaser.visibility = View.GONE
    }

    private fun initCollapsingToolbar() {
        binding.appBar.addOnOffsetChangedListener(this)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        with(binding) {
            collapsingToolbar.title = ""
            appBar.setExpanded(true)
            toolbar.inflateMenu(R.menu.favorite_detail)
            collapsingToolbar.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        }

        setMenuItemListener()
    }

    private fun setMenuItemListener() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_favorite -> {
                    saveFavoriteMovie(it)
                    true
                }
                else -> false
            }
        }
    }

    private fun saveFavoriteMovie(item: MenuItem) {
        setIconByIsFavorite(item)
        viewmodel.saveOrDeleteFavoriteMovie(_movie)
    }

    private fun setIconByIsFavorite(item: MenuItem) {
        val favorite = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
        val unFavorite =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)
        if (isFavorite) {
            item.icon = unFavorite
        } else {
            item.icon = favorite
        }
    }

    private fun setInitIconMovieSave(item: MenuItem){
        val favorite = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
        val unFavorite =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)
        if (isFavorite) {
            item.icon = favorite
        } else {
            item.icon = unFavorite
        }
    }

    private fun openIntenActionView(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}