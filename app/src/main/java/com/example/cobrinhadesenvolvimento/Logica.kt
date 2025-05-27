package com.example.cobrinhadesenvolvimento
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
class Game(){
    // variáveis
    var cobra by mutableStateOf(
        mutableListOf(Pair(7,7),Pair(7,6),Pair(7,5))
    )
    var direcaoAtual by mutableStateOf(Pair(0,1))
    var posicaoX by mutableStateOf(cobra[0].first)
    var posicaoY by mutableStateOf(cobra[0].second)
    var novaCabeca by mutableStateOf(Pair(posicaoX,posicaoY))
    var comida by mutableStateOf(Pair(5,5))
    var tamanho by mutableStateOf(3)
    var gameOver by mutableStateOf(false)

    // ação
    suspend fun roda(){
        while(true){
            posicaoX=posicaoX + direcaoAtual.first
            posicaoY=posicaoY + direcaoAtual.second
            posicaoX=(posicaoX + 16) % 16
            posicaoY=(posicaoY + 16) % 16
            novaCabeca =Pair(posicaoX,posicaoY)
            if(!(cobra.contains(novaCabeca))){
                cobra.add(0, novaCabeca)
                if (novaCabeca == comida) {
                    comida = Pair((0..15).random(), (0..15).random())
                    tamanho++
                }
                cobra = cobra.take(tamanho).toMutableList()
            }else{
                gameOver=true
                reset()
            }
            delay(500)
        }
    }
    //reset
    fun reset(){
        tamanho = 3
        cobra = mutableListOf(Pair(7,7),Pair(7,6),Pair(7,5))
        direcaoAtual = Pair(0,1)
        novaCabeca = Pair(7,7)
        posicaoX = (novaCabeca.first)
        posicaoY = (novaCabeca.second)
    }
}