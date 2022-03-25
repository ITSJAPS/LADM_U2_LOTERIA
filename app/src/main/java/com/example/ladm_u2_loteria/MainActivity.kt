package com.example.ladm_u2_loteria

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ladm_u2_loteria.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val act =this
    var baraja = ArrayList<Int>()

    lateinit var binding : ActivityMainBinding

   val audios = arrayOf(R.raw.gallo,R.raw.diablito,R.raw.dama,R.raw.catrin,R.raw.paraguas,R.raw.sirena,R.raw.escalera,R.raw.botella,R.raw.barril,
        R.raw.arbol,R.raw.melon,R.raw.valiente,R.raw.gorrito,R.raw.muerte,R.raw.pera,R.raw.bandera,R.raw.bandolon,R.raw.violoncello,R.raw.garza,
        R.raw.pajaro,R.raw.mano,R.raw.bota,R.raw.luna,R.raw.cotorro,R.raw.borracho,R.raw.negrito,R.raw.corazon,R.raw.sandia,R.raw.tambor,R.raw.camaron ,
        R.raw.jaras,R.raw.musico,R.raw.arana,R.raw.soldado,R.raw.estrella,R.raw.cazo,R.raw.mundo,R.raw.apache,R.raw.nopal,R.raw.alacran,R.raw.rosa,
        R.raw.calavera,R.raw.campana,R.raw.cantarito,R.raw.venado,R.raw.sol,R.raw.corona,R.raw.chalupa,R.raw.pino,R.raw.pescado,R.raw.palma,R.raw.maceta,
        R.raw.arpa,R.raw.rana)

    val imgCartas = arrayOf(R.drawable.carta1,R.drawable.carta2,R.drawable.carta3,R.drawable.carta4,R.drawable.carta5,
        R.drawable.carta6,R.drawable.carta7,R.drawable.carta8,R.drawable.carta9,R.drawable.carta10,
        R.drawable.carta11,R.drawable.carta12,R.drawable.carta13,R.drawable.carta14,R.drawable.carta15,
        R.drawable.carta16,R.drawable.carta17,R.drawable.carta18,R.drawable.carta19,R.drawable.carta20,
        R.drawable.carta21,R.drawable.carta22,R.drawable.carta23,R.drawable.carta24,R.drawable.carta25,
        R.drawable.carta26,R.drawable.carta27,R.drawable.carta28,R.drawable.carta29,R.drawable.carta30,
        R.drawable.carta31,R.drawable.carta32,R.drawable.carta33,R.drawable.carta34,R.drawable.carta35,
        R.drawable.carta36,R.drawable.carta37,R.drawable.carta38,R.drawable.carta39,R.drawable.carta40,
        R.drawable.carta41,R.drawable.carta42,R.drawable.carta43,R.drawable.carta44,R.drawable.carta45,
        R.drawable.carta46,R.drawable.carta47,R.drawable.carta48,R.drawable.carta49,R.drawable.carta50,
        R.drawable.carta51,R.drawable.carta52,R.drawable.carta53,R.drawable.carta54)



    var mpCancion: MediaPlayer?=null
    var detener=false

    //variables para el JOB segundo plano Asincrono
    var scope= CoroutineScope(Job() + Dispatchers.Main)

    var corriendo=true
    var indice=1
    var con =1




    override fun onCreate(savedInstanceState: Bundle?) {

        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        act.setTitle("Loteria ")
        var hilo = Hilo(act)


        // binding.rep.setOnClickListener(this::play)

        binding.rep.setOnClickListener{

            act.setTitle("Loteria ¡Se va y se corre...!")
            binding.loteria.setText("¡Loteria!")
            corriendo=true
            con=1
            act.binding.mensaje2.setText("Juego no. ${indice}")

            val jobCorrutina = scope.launch(EmptyCoroutineContext, CoroutineStart.LAZY) {

                while (corriendo) {
                    // hilo.pausarHilo()
                    (0..audios.size).forEach() {// 54 cartas
                        if (detener) { //pause
                            delay(6000L)
                            // mpCancion?.pause()
                        } else {
                            try {
                                mpCancion = MediaPlayer.create(act, audios[baraja[it]])

                            } catch (e: Exception) {
                            }
                            try {
                                runOnUiThread {
                                    act.binding.carta.setImageResource(imgCartas[baraja[it]])
                                    mpCancion?.start()
                                    binding.mensaje.setText("SE JUEGA ${con++} CARTAS")
                                }
                                delay(2000L)

                            } catch (e: Exception) {
                                mpCancion?.stop()
                            }
                        }
                    }

                    corriendo = false
                    indice++
                    delay(1000L)
                }
            }


            if (corriendo==true) {
                barajear()
                //hilo.start()

                jobCorrutina.start()
                // AlertDialog.Builder(this).setMessage("numeros generados: ${baraja.size} \n  numero de audios: ${audios.size} \n  numero de imagenes: ${imgCartas.size}").show()

                binding.rep.isEnabled = false
                binding.loteria.isEnabled = true
                return@setOnClickListener

            }/*else{


                        binding.loteria.isEnabled = false
                        return@setOnClickListener
                    }
                    */

        }


        binding.loteria.setOnClickListener{
            if (!detener) {
                detener = true
                binding.loteria.setText("Restantes")
                binding.mensaje.setText("¡LOTERIA!")
                act.setTitle("Loteria han ¡GANADO!")
                return@setOnClickListener
            }
            if(detener){
                detener=false
                binding.loteria.setText("Espera a que termine ")
                binding.rep.isEnabled = true
                return@setOnClickListener
            }
            /* if (!corriendo){
                /* barajar()
                 jobCorrutina.start()

                 */
                 binding.rep.isEnabled=true
                 binding.loteria.isEnabled=true
                 binding.loteria.setText("¡LOTERIA!")
                 return@setOnClickListener

             }

             */

        }





    }



    fun barajear(){
        baraja = ArrayList<Int>()
        for (m in audios){
            baraja.add(azar(baraja))
        }

    }

    fun azar(z:ArrayList<Int>):Int{
        var dig = Random.nextInt(54)
        while (!z.contains(dig)){
            return dig
        }
        return  azar(z)
    }


}