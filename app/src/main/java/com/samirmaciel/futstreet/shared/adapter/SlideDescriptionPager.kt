package com.samirmaciel.futstreet.shared.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.samirmaciel.futstreet.R

class SlideDescriptionPager(private val mContext : Context, var mStringList : MutableList<Int>) : PagerAdapter() {


    override fun getCount(): Int {
        return mStringList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.slide_description, null)

        val textDescription = view.findViewById<TextView>(R.id.textDescription)

        textDescription.setText(mContext.resources.getString(mStringList[position]))

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}