package com.assessment.soonet.ui.dashboard

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assessment.soonet.data.model.Photo
import com.assessment.soonet.databinding.PhotoItemBinding
import com.assessment.soonet.util.set
import kotlinx.coroutines.Job
import kotlin.math.roundToInt

class DashboardAdapter : PagingDataAdapter<Photo, DashboardAdapter.ViewHolder>(DashboardAdapter) {

    private var job: Job? = null

    private companion object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val biding = PhotoItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(biding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.viewBiding(it) }
    }

    inner class ViewHolder(
        private val biding: PhotoItemBinding,
    ) : RecyclerView.ViewHolder(biding.root) {

        fun viewBiding(photo: Photo) {

            biding.apply {

                // get aspect ratio from width and height
                val ratio: Float = photo.width.toFloat() / photo.height.toFloat()

                // get screen width
                val width: Int = Resources.getSystem().displayMetrics.widthPixels

                // calculate height
                val height: Float = width / ratio

                imageView.layoutParams.height = height.toInt()
                imageView.set(photo.downloadUrl, progressPhoto)
                textViewAuthor.text = "By ${photo.author}"

            }
        }
    }

    fun jobCancel() {
        job?.cancel()
    }
}
