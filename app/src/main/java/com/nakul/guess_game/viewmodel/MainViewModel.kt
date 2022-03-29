package com.nakul.guess_game.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nakul.guess_game.models.GameState
import com.nakul.guess_game.models.State
import com.nakul.guess_game.models.TileState

/**
 * Created by Nakul
 * on 29,March,2022
 */
class MainViewModel : ViewModel() {

    private val arrayList = ArrayList<TileState>()
    private val mList = MutableLiveData<ArrayList<TileState>>()
    val list: LiveData<ArrayList<TileState>> = mList

    private var chancesInt = 3
    private val mChances = MutableLiveData(chancesInt)
    val chances: LiveData<Int> = mChances

    private val mNum = MutableLiveData(3)
    val num: LiveData<Int> = mNum

    private val mState = MutableLiveData(GameState.PLAYING)
    val state: LiveData<GameState> = mState

    init {
        resetData()
    }

    fun resetData() {
        mState.postValue(GameState.PLAYING)
        chancesInt = 3
        mChances.value = chancesInt
        arrayList.clear()
        for (i in 1..9)
            arrayList.add(TileState(i, State.HIDDEN))
        arrayList.shuffle()
        mNum.value = (1..9).random()
        mList.value = arrayList
    }

    fun clicked(ind: Int) {
        assert(ind < arrayList.size)
        if (arrayList[ind].state == State.SHOWN || chances.value == 0 || state.value != GameState.PLAYING)
            return
        arrayList[ind] = arrayList[ind].copy(state = State.SHOWN)
        if (arrayList[ind].num == num.value) {
            mState.value = GameState.WON
        }
        reduceChance()
        if (chances.value != 0)
            mList.postValue(arrayList)
    }

    private fun reduceChance() {
        mChances.postValue(chances.value?.minus(1))
        chancesInt--
        if (chancesInt == 0 && state.value == GameState.PLAYING) {
            Handler(Looper.getMainLooper()).postDelayed({
                arrayList.forEachIndexed { i, _ ->
                    arrayList[i] = arrayList[i].copy(state = State.SHOWN)
                }
                mList.postValue(arrayList)
                Handler(Looper.getMainLooper()).postDelayed({
                    mState.postValue(GameState.LOST)
                }, 100L)
            }, 300L)
        }
    }
}