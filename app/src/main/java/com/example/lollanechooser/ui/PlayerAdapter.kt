package com.example.lollanechooser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lollanechooser.R
import com.example.lollanechooser.common.Config
import com.example.lollanechooser.databinding.ItemPlayerBinding
import com.example.lollanechooser.model.Player

class PlayerAdapter(private val mPlayerSet: ArrayList<Player>) :
    RecyclerView.Adapter<PlayerAdapter.MyViewHolder>() {

    private lateinit var mContext: Context

    inner class MyViewHolder(private val binding: ItemPlayerBinding) :
//    inner class MyViewHolder(private val binding: ItemPlayerIconBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Player, position: Int) {

            with(binding) {
                playerName.text = data.name

                if (data.top)
                    playerLineSelector.check(R.id.line_top)
                if (data.jgl)
                    playerLineSelector.check(R.id.line_jgl)
                if (data.mid)
                    playerLineSelector.check(R.id.line_mid)
                if (data.bot)
                    playerLineSelector.check(R.id.line_bot)
                if (data.spt)
                    playerLineSelector.check(R.id.line_spt)


                playerLineSelector.addOnButtonCheckedListener { group, checkedId, isChecked ->

                    when (checkedId) {
                        R.id.line_top -> {
                            Config.mPlayerList[position].top = isChecked
                        }

                        R.id.line_jgl -> {
                            Config.mPlayerList[position].jgl = isChecked
                        }

                        R.id.line_mid -> {
                            Config.mPlayerList[position].mid = isChecked
                        }

                        R.id.line_bot -> {
                            Config.mPlayerList[position].bot = isChecked
                        }

                        R.id.line_spt -> {
                            Config.mPlayerList[position].spt = isChecked
                        }
                    }

                }


            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        mContext = parent.context
        val binding =
            ItemPlayerBinding.inflate(LayoutInflater.from(mContext), parent, false)
//            ItemPlayerIconBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mPlayerSet[position], position)
    }

    override fun getItemCount(): Int = mPlayerSet.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
}