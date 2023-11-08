package com.jejetrue.githubjj.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jejetrue.githubjj.R
import com.jejetrue.githubjj.data.adapter.SectionPagerAdapter
import com.jejetrue.githubjj.data.response.DetailUserResponse
import com.jejetrue.githubjj.databinding.ActivityDetailBinding
import com.jejetrue.githubjj.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITTLE = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME).toString()


        detailViewModel.findUser(username)


        detailViewModel.detailUser.observe(this){
            setDataUser(it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position->
            tab.text = resources.getString(TAB_TITTLE[position])
        }.attach()
        detailViewModel.isLoadingDetail.observe(this){
            showLoading(it)
        }


    }
    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
    private fun setDataUser(userItem: DetailUserResponse){
        binding.apply {
            Glide.with(applicationContext)
                .load(userItem.avatarUrl)
                .into(imgProfile)
            tvFullname.text = userItem.name
            tvUsername.text = userItem.login
            tvFollower.text = userItem.followers.toString()
            tvFollowings.text = userItem.following.toString()
        }
    }
}