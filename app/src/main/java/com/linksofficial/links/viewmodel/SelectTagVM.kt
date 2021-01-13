package com.linksofficial.links.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.linksofficial.links.data.model.Tags

class SelectTagVM(): ViewModel(){

    var selectTag = ObservableField<Tags>()

    fun selected(v:View){
        selectTag.get()?.selected = !selectTag.get()?.selected!!
        selectTag.notifyChange()
    }


}