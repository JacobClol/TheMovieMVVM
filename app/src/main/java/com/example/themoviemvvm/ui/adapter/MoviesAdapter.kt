package com.example.themoviemvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviemvvm.R
import com.example.themoviemvvm.core.bases.BaseViewHolder
import com.example.themoviemvvm.databinding.ItemRowMovieBinding
import com.example.themoviemvvm.domain.models.Movie

class MoviesAdapter(
    private val context: Context,
    private val movieClickListener: OnMovieClickListener,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val listMovies: ArrayList<Movie> = arrayListOf()
    private var isFavoriteList: Boolean = false

    interface OnMovieClickListener {
        fun onMovieClick(idMovie: Int)
        fun onFavoriteClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemRowMovieBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(listMovies[position], position)
        }
    }

    override fun getItemCount() = listMovies.size

    inner class MainViewHolder(private val binding: ItemRowMovieBinding) :
        BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie, position: Int): Unit = with(binding) {
            val urlImage = "https://image.tmdb.org/t/p/w500${item.posterPath}"
            Glide.with(context).load(urlImage).placeholder(R.drawable.load).into(thumbnail)
            title.text = item.title
            populatiry.text = item.popularity.toString()
            voteCount.text = item.voteCount.toString()
            rating.text = item.voteAverage.toString()
            itemView.setOnClickListener {
                if (isFavoriteList){
                    movieClickListener.onFavoriteClick(position)
                } else {
                    movieClickListener.onMovieClick(item.id)
                }
            }
        }
    }

    fun submitList(movies: Collection<Movie>){
        isFavoriteList = false
        listMovies.clear()
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    fun submitListFavorite(movies: Collection<Movie>){
        isFavoriteList = true
        listMovies.clear()
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    fun orderByPopularity(){
        val listNewOrder = listMovies.sortedByDescending { it.popularity }
        listMovies.clear()
        listMovies.addAll(listNewOrder)
        notifyDataSetChanged()
    }

    fun orderByRate(){
        val listNewOrder = listMovies.sortedByDescending { it.voteAverage }
        listMovies.clear()
        listMovies.addAll(listNewOrder)
        notifyDataSetChanged()
    }

    fun orderByVote(){
        val listNewOrder = listMovies.sortedByDescending { it.voteCount }
        listMovies.clear()
        listMovies.addAll(listNewOrder)
        notifyDataSetChanged()
    }
}