package com.example.nerdlauncher.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nerdlauncher.R
import de.hdodenhof.circleimageview.CircleImageView

class LaunchAdapter : RecyclerView.Adapter<LaunchAdapter.LaunchViewHolder>() {

    private var activities: MutableList<ResolveInfo> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setActivities(list: MutableList<ResolveInfo>){
        activities.clear()
        activities.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_item, parent, false)
        return LaunchViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bindActivity(activities[position])
    }

    override fun getItemCount() = activities.size

    class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }
        private var iv_app = itemView.findViewById(R.id.iv_app) as CircleImageView
        private var tv_app = itemView.findViewById(R.id.tv_app) as TextView
        private lateinit var resolveInfo: ResolveInfo

        fun bindActivity(resolveInfo: ResolveInfo){
            this.resolveInfo = resolveInfo
            val packageManager = itemView.context.packageManager
            val appName = resolveInfo.loadLabel(packageManager).toString()
            val appIcon = resolveInfo.loadIcon(packageManager)
            iv_app.setImageDrawable(appIcon)
            tv_app.text = appName

        }

        override fun onClick(view: View) {
            val activityInfo = resolveInfo.activityInfo
            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            view.context.startActivity(intent)
        }

    }

}