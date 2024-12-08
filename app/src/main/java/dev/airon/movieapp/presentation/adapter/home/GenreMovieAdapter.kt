package dev.airon.movieapp.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.airon.movieapp.databinding.GenreItemBinding
import dev.airon.movieapp.presentation.model.GenrePresetation

class GenreMovieAdapter :
    ListAdapter<GenrePresetation, GenreMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenrePresetation>() {

            override fun areItemsTheSame(
                oldItem: GenrePresetation,
                newItem: GenrePresetation
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GenrePresetation,
                newItem: GenrePresetation
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            GenreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.binding.root.context
        val genre = getItem(position)
        holder.binding.genreMovie.text = genre.name
        val movieAdapter = MovieAdapter(context)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.recyclerMovie.layoutManager = layoutManager
        holder.binding.recyclerMovie.setHasFixedSize(true)
        holder.binding.recyclerMovie.adapter = movieAdapter
        movieAdapter.submitList(genre.moveis)

    }

    inner class MyViewHolder(val binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root)
}