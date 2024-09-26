package com.note.shop.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.note.shop.model.CategoryModel
import com.note.shop.model.ItemsModel
import com.note.shop.model.SliderModel

class MainViewModel() : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<UiState<List<SliderModel>>>()
    private val _category = MutableLiveData<UiState<MutableList<CategoryModel>>>()
    private val _recommended = MutableLiveData<UiState<MutableList<ItemsModel>>>()

    val banners: LiveData<UiState<List<SliderModel>>> = _banner
    val categories: LiveData<UiState<MutableList<CategoryModel>>> = _category
    val recommended: LiveData<UiState<MutableList<ItemsModel>>> = _recommended



    fun loadFiltered(id:String) {
        _recommended.value = UiState.Loading
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("categoryId").equalTo(id)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _recommended.value = UiState.Success(lists)
            }

            override fun onCancelled(error: DatabaseError) {
                _recommended.value = UiState.Error(error.message)
            }
        })
    }
    fun loadRecommended() {
        _recommended.value = UiState.Loading
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("showRecommended").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _recommended.value = UiState.Success(lists)
            }

            override fun onCancelled(error: DatabaseError) {
                _recommended.value = UiState.Error(error.message)
            }
        })
    }

    fun loadCategory() {
        _category.value = UiState.Loading
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(CategoryModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _category.value = UiState.Success(lists)
            }

            override fun onCancelled(error: DatabaseError) {
                _category.value = UiState.Error(error.message)
            }
        })
    }

    fun loadBanners() {
        _banner.value = UiState.Loading
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _banner.value = UiState.Success(lists)
            }

            override fun onCancelled(error: DatabaseError) {
                _banner.value = UiState.Error(error.message)
            }
        })
    }
}
