package com.example.wallpaper

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallpaper.models.ImageModel

import android.widget.Toast

import io.reactivex.schedulers.Schedulers

import io.reactivex.android.schedulers.AndroidSchedulers

import com.example.wallpaper.models.SearchModel


import android.view.Menu
import android.view.MenuItem
import android.view.View


import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView


import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.GridLayoutManager

import io.reactivex.disposables.CompositeDisposable

import androidx.cardview.widget.CardView

import io.reactivex.functions.BiConsumer
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import android.widget.Adapter as Adapter1


class MainActivity : AppCompatActivity(), OnDataReceived {
    private val modelArrayList: MutableList<ImageModel> = mutableListOf()
    private var page = 1
    private var recyclerView: RecyclerView? = null
    var adapter: Adapter? = null
    var d_3: CardView? = null
    var textures: CardView? = null
    var nature: CardView? = null
    var food: CardView? = null
    var travel: CardView? = null
    var animals: CardView? = null
    private var isLoading = false
    private var isLastPage = false
    private val pageSize = 30
    var gridLayoutManager: GridLayoutManager? = null
    var searchView: SearchView? = null
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    var disposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.setLayoutManager(gridLayoutManager)
        //recyclerView!!.layoutManager = gridLayoutManager
        recyclerView!!.setHasFixedSize(true)
//        adapter = modelArrayList?.let { it1 -> Adapter(applicationContext, modelArrayList!!) }
//        recyclerView!!.setAdapter(adapter as Adapter)
        adapter = Adapter(applicationContext, modelArrayList!!)
        recyclerView!!.adapter = adapter
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItem = gridLayoutManager!!.childCount
                val totalItem = gridLayoutManager!!.itemCount
                val firstVisibleItem = gridLayoutManager!!.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItem + firstVisibleItem >= totalItem && firstVisibleItem >= 0 && totalItem >= pageSize) {
                        page++
                        findPhotos()
                    }
                }
            }
        })
        findPhotos()
        d_3!!.setOnClickListener { view: View? ->
            val q = "3d"
            getSearchImage(q)
        }
        textures!!.setOnClickListener { view: View? ->
            val q = "textures"
            getSearchImage(q)
        }
        nature!!.setOnClickListener { view: View? ->
            val q = "nature"
            getSearchImage(q)
        }
        food!!.setOnClickListener { view: View? ->
            val q = "food"
            getSearchImage(q)
        }
        travel!!.setOnClickListener { view: View? ->
            val q = "travel"
            getSearchImage(q)
        }
        animals!!.setOnClickListener { view: View? ->
            val q = "animals"
            getSearchImage(q)
        }
    }

    private fun initialization() {
        recyclerView = findViewById(R.id.recycler_view)
        d_3 = findViewById(R.id.nature)
        textures = findViewById(R.id.bus)
        nature = findViewById(R.id.car)
        food = findViewById(R.id.train)
        travel = findViewById(R.id.trending)
        animals = findViewById(R.id.animals)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.optin_menu, menu)
        val search: MenuItem = menu.findItem(R.id.search)
        searchView = search.getActionView() as SearchView
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            @RequiresApi(Build.VERSION_CODES.P)
            override fun onQueryTextSubmit(query: String): Boolean {
                AppExecutor.getInstances()
                AppExecutor.getInstances().getSubIO().execute(Runnable { getSearchImage(query) })
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length == 0) {
                    modelArrayList!!.clear()
                    findPhotos()
                    adapter?.notifyDataSetChanged()
                }
                return false
            }
        })
        return true
    }

    override fun onBackPressed() {
        if (searchView?.isIconified() == true) {
            searchView!!.setIconified(true)
            modelArrayList.clear()
            findPhotos()
        }
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast!!.cancel()
            super.onBackPressed()
            return
        } else {
            backToast =
                Toast.makeText(baseContext, "Нажмите еще раз чтобы выйти", Toast.LENGTH_SHORT)
            with(backToast) { this?.show() }
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun getSearchImage(query: String) {
        isLoading = true
        val app = application as App
        app.unsplashService!!.apiIntephase!!.searchImages(query, 10000) //RxJava
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())?.let { it1 ->
                disposable.add(
                it1
                    .subscribe(object : BiConsumer<SearchModel?, Throwable?> {
                        @SuppressLint("NotifyDataSetChanged")

                        @Throws(Exception::class)
//                        fun accept(searchModel: SearchModel, throwable: Throwable?) {
//                            if (throwable != null) {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Not able to get",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else {
//                                modelArrayList.clear()
//                                modelArrayList.addAll(searchModel.results!!) //modelArrayList!!.addAll(searchModel.getResults()!!)
//                                adapter?.notifyDataSetChanged()
//                            }
//                            isLoading = false
//                            if (modelArrayList!!.size > 0) {
//                                isLastPage = modelArrayList!!.size < pageSize
//                            } else isLastPage = true
//                        }

                        override fun accept(searchModel: SearchModel?, throwable: Throwable?) {
                            if (throwable != null) {
                                Toast.makeText(
                                    applicationContext,
                                    "Not able to get",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                modelArrayList.clear()
                                modelArrayList.addAll(searchModel?.results!!) //modelArrayList!!.addAll(searchModel.getResults()!!)
                                adapter?.notifyDataSetChanged()
                            }
                            isLoading = false
                            if (modelArrayList!!.size > 0) {
                                isLastPage = modelArrayList!!.size < pageSize
                            } else isLastPage = true
                        }
                    })
            )
            }
    }

    private fun findPhotos() {
        isLoading = true
        val app = application as App
        app.unsplashService?.apiIntephase?.getImages(page, 10000) //RxJava
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(object : BiConsumer<List<ImageModel>?, Throwable?> {
                @SuppressLint("NotifyDataSetChanged")
                @Throws(Exception::class)
                    override fun accept(imageModels: List<ImageModel>?, throwable: Throwable?) {
                    if (throwable != null) {
                        Toast.makeText(
                            applicationContext,
                            "Not able to get",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        modelArrayList!!.addAll(imageModels ?: mutableListOf())
                        adapter!!.notifyDataSetChanged()
                    }
                    isLoading = false
                    if (modelArrayList!!.size > 0) {
                        isLastPage = modelArrayList!!.size < pageSize
                    } else isLastPage = true
                }
            })?.let { it1 ->
                disposable.add(
                    it1
                )
            }
    }

    override fun onReceived(list: List<ImageModel?>?) {
        AppExecutor.getInstances().getMainIO().execute(Runnable {
            adapter?.updateAdapter(
                list as List<ImageModel>?
            )
        })
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun Adapter1?.notifyDataSetChanged() {
//        modelArrayList!!.clear()
//         //modelArrayList!!.addAll(searchModel.getResults()!!)
//        adapter?.notifyDataSetChanged()
    }
}


